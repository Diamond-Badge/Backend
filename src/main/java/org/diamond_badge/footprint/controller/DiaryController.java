package org.diamond_badge.footprint.controller;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.model.ListResult;
import org.diamond_badge.footprint.model.SingleResult;
import org.diamond_badge.footprint.service.DiaryService;
import org.diamond_badge.footprint.service.ResponseService;
import org.diamond_badge.footprint.vo.DiaryRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"2. Diary"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/diary")
public class DiaryController {

	private final DiaryService diaryService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value="전체조회",notes = "지도 전송 어제 6시부터 오늘 오전 6시까지")
	@GetMapping("/map")
	public ListResult<Diary> getDiarys(){
		return responseService.getListResult(diaryService.findDiarys());
	}

	//위치 등록 -> 처음 등록이면 timeLine 자동생성 / 위치 추가
	@ApiOperation(value="위치 등록",notes = "위치 등록/위치 추가")
	@PostMapping("/")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public SingleResult<Diary> createDiary(
		@RequestHeader("Autorization") String AuthToken,
		@RequestBody DiaryRequest diaryRequest){
		String email= jwtTokenProvider.getUserPk(AuthToken);
		System.out.println(email);
		return responseService.getSingleResult(diaryService.createDiary(diaryRequest,email));
	}

	//글내용수정
	@ApiOperation(value="글내용 수정",notes = "글내용 수정")
	@PostMapping("/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Autorization", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public SingleResult<Diary> updateDiary(
		@RequestHeader("Autorization") String AuthToken,
		@PathVariable Long diarySeq,
		@RequestParam("place") String place,
		@RequestParam("content") String content){
		return responseService.getSingleResult(diaryService.updateDiary(content,place,diarySeq));
	}

	//위치 삭제
	@ApiOperation(value = "위치 삭제", notes = "위치를 삭제한다.")
	@DeleteMapping("/{diarySeq}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
	})
	public CommonResult deleteDiary(
		@PathVariable long diarySeq,
		@RequestHeader("X-AUTH-TOKEN") String xAuthToken) {
		return responseService.getSingleResult(diaryService.deleteDiary(diarySeq));
	}

	//좋아요기능

}
