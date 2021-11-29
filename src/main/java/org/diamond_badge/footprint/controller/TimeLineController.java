package org.diamond_badge.footprint.controller;

import java.time.LocalDate;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.model.ListResult;
import org.diamond_badge.footprint.model.SingleResult;
import org.diamond_badge.footprint.service.ResponseService;
import org.diamond_badge.footprint.service.TimeLineService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
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

@Api(tags = {"2. TimeLine"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/timeline")
public class TimeLineController {

	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;
	private final TimeLineService timeLineService;

	@ApiOperation(value = "타임라인 단건조회(id)", notes = "타임라인을 id를 통해 불러옵니다.")
	@GetMapping("/{timeLineSeq}")
	public SingleResult<TimeLine> getTodayTimeLine(
		@PathVariable long timeLineSeq) {
		return responseService.getSingleResult(timeLineService.getTimeLineByid(timeLineSeq));
	}

	@ApiOperation(value = "타임라인 단건조회(캘린더)", notes = "타임라인을 날짜를 통해 불러옵니다.")
	@GetMapping("")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public SingleResult<TimeLine> getTodayTimeLine(
		@RequestHeader("Autorization") String AuthToken,
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		return responseService.getSingleResult(timeLineService.getTimeLine(email, localDate));
	}

	@ApiOperation(value = "타임라인 주", notes = "타임라인을 주 단위로 불러옵니다.")
	@GetMapping("/week")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public ListResult<TimeLine> getWeekTimeLine(
		@RequestHeader("Autorization") String AuthToken,
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		return responseService.getListResult(timeLineService.findTimeLinesByWeek(email, localDate));
	}

	@ApiOperation(value = "타임라인 월", notes = "타임라인을 월 단위로 불러옵니다.")
	@GetMapping("/month")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public ListResult<TimeLine> getMonthTimeLine(
		@RequestHeader("Autorization") String AuthToken,
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
		String email = jwtTokenProvider.getUserPk(AuthToken);
		return responseService.getListResult(timeLineService.findTimeLinesByMonth(email, localDate));
	}

	@ApiOperation(value = "감정 등록", notes = "감정 등록/수정")
	@PostMapping("/{timeLineSeq}/emotion")
	public SingleResult<TimeLine> registerEmotion(
		@PathVariable long timeLineSeq,
		@RequestParam String emotionName) {
		return responseService.getSingleResult(timeLineService.setEmotion(emotionName, timeLineSeq));
	}

}
