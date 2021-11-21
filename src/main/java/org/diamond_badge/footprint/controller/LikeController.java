package org.diamond_badge.footprint.controller;

import java.util.List;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.model.ListResult;
import org.diamond_badge.footprint.service.LikeService;
import org.diamond_badge.footprint.service.ResponseService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/like")
public class LikeController {

	private final LikeService likeService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;

	@ApiOperation(value = "좋아요", notes = "좋아요")
	@PostMapping("/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult addLike(
		@RequestHeader("Autorization") String AuthToken,
		@PathVariable Long diarySeq) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		boolean result = likeService.addLike(email, diarySeq);
		if (result) {
			return responseService.getSuccessResult();
		} else {
			return responseService.getDefaultFailResult("좋아요 삭제했습니다.");
		}
	}

	@ApiOperation(value = "좋아요 취소", notes = "좋아요")
	@DeleteMapping("/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult deleteLike(
		@RequestHeader("Autorization") String AuthToken,
		@PathVariable Long diarySeq) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		likeService.cancelLike(email, diarySeq);
		return responseService.getSuccessResult();
	}

	@ApiOperation(value = "좋아요 카운트")
	@GetMapping("/like/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public ListResult<String> getLikeCount(@PathVariable Long diarySeq,
		@RequestHeader("Autorization") String AuthToken) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		List<String> resultData = likeService.count(email, diarySeq);

		return responseService.getListResult(resultData);
	}

}
