package org.diamond_badge.footprint.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.diamond_badge.footprint.advice.exception.InvalidRefreshTokenException;
import org.diamond_badge.footprint.advice.exception.NotExpiredTokenYetException;
import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.jpa.entity.RoleType;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.entity.UserRefreshToken;
import org.diamond_badge.footprint.jpa.repo.UserRefreshTokenRepository;
import org.diamond_badge.footprint.model.SingleResult;
import org.diamond_badge.footprint.model.util.CookieUtil;
import org.diamond_badge.footprint.service.ResponseService;
import org.diamond_badge.footprint.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
public class SignController {

	private final static long THREE_DAYS_MSEC = 259200000;
	private final static String REFRESH_TOKEN = "refresh_token";
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final UserService userService;
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	@ApiOperation(value = "운영자 로그인", notes = "운영자 계정을 통해 로그인한다.")
	@PostMapping(value = "/signin")
	public SingleResult<String> userlogin(
		String id, String password, HttpServletRequest request,
		HttpServletResponse response) throws Throwable {
		String refreshToken = jwtTokenProvider.createRefreshToken(id, RoleType.ADMIN);

		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserEmail(id);
		if (userRefreshToken == null) {
			userRefreshToken = new UserRefreshToken(id, refreshToken);
			userRefreshTokenRepository.saveAndFlush(userRefreshToken);
		} else {
			userRefreshToken.setRefreshToken(refreshToken);
		}
		long refreshTokenExpiry = jwtTokenProvider.getREFRESH_EXPIRATION();
		int cookieMaxAge = (int)refreshTokenExpiry / 60;
		CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken, cookieMaxAge);
		return responseService.getSingleResult(
			jwtTokenProvider.createToken(id, RoleType.ADMIN));
	}

	@ApiOperation(value = "소셜 로그인", notes = " 소셜 회원 로그인을 한다.")
	@PostMapping(value = "/signin/{provider}")
	public SingleResult<String> signinByProvider(
		HttpServletRequest request,
		HttpServletResponse response,
		@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
		@ApiParam(value = "소셜 accessToken", required = true) @RequestParam String accessToken) throws Exception {

		User signedUser = null;

		switch (provider) {
			case "naver":
				signedUser = userService.signupByNaver(accessToken, provider);
				break;
			case "kakao":
				signedUser = userService.signupByKakao(accessToken, provider);
				break;
			case "google":
				signedUser= userService.signupByGoogle(accessToken, provider);
				break;
		}

		String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(signedUser.getEmail()),
			signedUser.getRoleType());

		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserEmail(signedUser.getEmail());
		if (userRefreshToken == null) {
			userRefreshToken = new UserRefreshToken(signedUser.getEmail(), refreshToken);
			userRefreshTokenRepository.saveAndFlush(userRefreshToken);
		} else {
			userRefreshToken.setRefreshToken(refreshToken);
		}
		long refreshTokenExpiry = jwtTokenProvider.getREFRESH_EXPIRATION();
		int cookieMaxAge = (int)refreshTokenExpiry / 60;
		CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken, cookieMaxAge);

		return responseService.getSingleResult(
			jwtTokenProvider.createToken(String.valueOf(signedUser.getEmail()), signedUser.getRoleType()));

	}

	@ApiOperation(value = "refreshToken 값 -> AccessToken 값", notes = "refreshToken 값을 이용해 AccessToken 값을 얻는다")
	@GetMapping("/refresh")
	public SingleResult<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		// access token 확인
		String accessToken = jwtTokenProvider.resolveToken(request).split(" ")[1].trim();
		System.out.println(accessToken);

		Claims claims = jwtTokenProvider.getExpiredTokenClaims(accessToken);
		if (claims == null) {
			throw new NotExpiredTokenYetException();
		}
		String userId = jwtTokenProvider.getUserPk(accessToken);
		RoleType roleType = RoleType.of(claims.get("role", String.class));

		// refresh token
		String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
			.map(Cookie::getValue)
			.orElse((null));

		if (jwtTokenProvider.getExpiredTokenClaims(accessToken).isEmpty()) {
			//오류반환
			throw new NotExpiredTokenYetException();
		}

		// userId refresh token 으로 DB 확인
		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserEmailAndRefreshToken(userId,
			refreshToken);
		if (userRefreshToken == null) {
			throw new InvalidRefreshTokenException();
			//요류반환
		}

		Date now = new Date();
		String newAccessToken = jwtTokenProvider.createToken(userId, roleType);

		long validTime = jwtTokenProvider.getExpiredTokenClaims(refreshToken).getExpiration().getTime() - now.getTime();

		// refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
		if (validTime <= THREE_DAYS_MSEC) {
			String newRefreshToken = jwtTokenProvider.createRefreshToken(userId, roleType);

			// DB에 refresh 토큰 업데이트
			userRefreshToken.setRefreshToken(newRefreshToken);
			long refreshTokenExpiry = jwtTokenProvider.getREFRESH_EXPIRATION();
			int cookieMaxAge = (int)refreshTokenExpiry / 60;
			CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
			CookieUtil.addCookie(response, REFRESH_TOKEN, newRefreshToken, cookieMaxAge);
		}

		return responseService.getSingleResult(newAccessToken);
	}
}
