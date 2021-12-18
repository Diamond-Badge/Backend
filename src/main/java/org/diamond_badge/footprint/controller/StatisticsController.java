package org.diamond_badge.footprint.controller;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.jpa.entity.Statistics;
import org.diamond_badge.footprint.model.SingleResult;
import org.diamond_badge.footprint.service.ResponseService;
import org.diamond_badge.footprint.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"8. Statistics"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/statistics")
public class StatisticsController {

	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final StatisticsService statisticsService;

	@ApiOperation(value = "통계 불러오기", notes = "통계 불러오기")
	@GetMapping("")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public SingleResult<Statistics> getDiarysByMonth(
		@RequestHeader("Autorization") String AuthToken) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		return responseService.getSingleResult(statisticsService.getStatistics(email));
	}

}
