package org.diamond_badge.footprint.controller;

import java.io.File;
import java.io.IOException;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.model.SingleResult;
import org.diamond_badge.footprint.service.FileService;
import org.diamond_badge.footprint.service.ResponseService;
import org.diamond_badge.footprint.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"5. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

	private final UserService userService;
	private final FileService fileService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;

	@ApiOperation(value = "마이페이지 불러오기", notes = "사용자 정보 불러오기")
	@GetMapping("")
	public SingleResult<User> getUser(
		@RequestHeader("Autorization") String AuthToken) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		return responseService.getSingleResult(userService.getUser(email));
	}

	@ApiOperation(value = "닉네임 수정", notes = "닉네임 등록 수정한다.")
	@PostMapping("/name")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult setUsername(
		@RequestHeader("Autorization") String AuthToken,
		@RequestParam String userName) throws IOException {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		userService.setUsername(email, userName);
		return responseService.getSuccessResult();
	}

	@ApiOperation(value = "프로필 사진 변경", notes = "프로필 사진 변경/등록")
	@PostMapping("/profile")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult setProfile(
		@RequestHeader("Autorization") String AuthToken,
		@RequestBody @ApiParam(value = "회원사진", required = true) MultipartFile file
	) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		File newFile = fileService.save(file, email);
		userService.setProfileUrl(email, newFile.getPath());
		return responseService.getSuccessResult();
	}

	@ApiOperation(value = "공개 설정", notes = "전체공개/비공개")
	@PostMapping("/isPublic")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult setPrivate(
		@RequestHeader("Autorization") String AuthToken
	) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		userService.setIsPublic(email);
		return responseService.getSuccessResult();
	}

}
