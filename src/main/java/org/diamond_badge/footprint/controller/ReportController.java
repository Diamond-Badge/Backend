package org.diamond_badge.footprint.controller;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.service.ReportService;
import org.diamond_badge.footprint.service.ResponseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"7. Report"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {

	private final ReportService reportService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;

	@ApiOperation(value = "신고등록", notes = "신고 등록 다이어리 신고 개수가 3 이상이면 isWritten을 false 한다.")
	@PostMapping("/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult reports(
		@RequestHeader("Autorization") String AuthToken,
		@PathVariable Long diarySeq,
		@RequestParam("reason") String reason) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		boolean result = reportService.reports(email, diarySeq, reason);
		if (result) {
			return responseService.getSuccessResult();
		} else {
			return responseService.getDefaultFailResult("이미 신고하셨습니다.");
		}
	}
}
