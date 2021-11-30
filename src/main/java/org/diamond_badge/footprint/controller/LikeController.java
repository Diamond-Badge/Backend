package org.diamond_badge.footprint.controller;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.service.LikeService;
import org.diamond_badge.footprint.service.ResponseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"4. Like"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/like")
public class LikeController {

	private final LikeService likeService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;

	@ApiOperation(value = "좋아요(등록 / 삭제)", notes = "한 번 클릭하면 좋아요 등록, 두 번 클릭하면 좋아요 삭제")
	@PostMapping("/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult likes(
		@RequestHeader("Autorization") String AuthToken,
		@PathVariable Long diarySeq) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		boolean result = likeService.likes(email, diarySeq);
		if (result) {
			return responseService.getSuccessResult();
		} else {
			return responseService.getDefaultFailResult("좋아요 삭제했습니다.");
		}
	}
}
