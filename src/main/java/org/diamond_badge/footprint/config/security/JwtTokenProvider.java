package org.diamond_badge.footprint.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.diamond_badge.footprint.jpa.entity.RoleType;
import org.springframework.core.env.Environment;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
	private final Environment env;
	private final UserDetailsService userDetailsService;
	private String SECRET_KEY;

	private long EXPIRATION_TIME;
	private long REFRESH_EXPIRATION;

	@PostConstruct
	protected void init() {
		SECRET_KEY = Base64.getEncoder().encodeToString(env.getProperty("spring.jwt.secret").getBytes());
		EXPIRATION_TIME = Long.parseLong(env.getProperty("spring.jwt.expiration_time"));
		REFRESH_EXPIRATION=Long.parseLong(env.getProperty("spring.jwt.refreshTokenExpiry"));

	}

	// Jwt 토큰 생성
	public String createToken(String userPk, RoleType roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims) // 데이터
			.setIssuedAt(now) // 토큰 발행일자
			.setExpiration(new Date(now.getTime() + EXPIRATION_TIME)) // set Expire Time
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 암호화 알고리즘, secret값 세팅
			.compact();
	}


	// Jwt 토큰 생성
	public String createRefreshToken(String userPk, RoleType roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims) // 데이터
			.setIssuedAt(now) // 토큰 발행일자
			.setExpiration(new Date(now.getTime() + REFRESH_EXPIRATION)) // set Expire Time
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 암호화 알고리즘, secret값 세팅
			.compact();
	}
	// Jwt 토큰으로 인증 정보를 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUserName(String token){
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return userDetails.getUsername();
	}

	// Jwt 토큰에서 회원 구별 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	public long getREFRESH_EXPIRATION(){
		return REFRESH_EXPIRATION;
	}

	// Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
	public String resolveToken(HttpServletRequest req) {
		return req.getHeader("Authorization");
	}

	// Jwt 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public Claims getExpiredTokenClaims(String jwtToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			return e.getClaims();
		}
		return null;
	}
}
