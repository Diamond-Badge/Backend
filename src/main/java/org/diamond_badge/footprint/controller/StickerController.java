package org.diamond_badge.footprint.controller;

import org.diamond_badge.footprint.config.security.JwtTokenProvider;
import org.diamond_badge.footprint.jpa.entity.Sticker;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.model.ListResult;
import org.diamond_badge.footprint.model.SingleResult;
import org.diamond_badge.footprint.model.vo.StickerRequest;
import org.diamond_badge.footprint.service.ResponseService;
import org.diamond_badge.footprint.service.StickerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"6. Sticker"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/sticker")
public class StickerController {

	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;
	private final StickerService stickerService;

	@ApiOperation(value = "스티커 조회", notes = "스티커 조회")
	@GetMapping("/{timeLineSeq}")
	public ListResult<Sticker> getStickers(
		@PathVariable Long timeLineSeq
	) {
		return responseService.getListResult(stickerService.findStickers(timeLineSeq));
	}

	@ApiOperation(value = "스티커 등록", notes = "스티커 등록")
	@PostMapping("/{timeLineSeq}")
	public SingleResult<Sticker> createSticker(
		@PathVariable Long timeLineSeq,
		@RequestBody StickerRequest stickerRequest) {
		return responseService.getSingleResult(stickerService.registerSticker(stickerRequest, timeLineSeq));
	}

	@ApiOperation(value = "스티커 삭제", notes = "스티커 삭제")
	@PostMapping("/{timeLineSeq}/{stickerSeq}")
	public CommonResult deleteSticker(
		@PathVariable Long timeLineSeq,
		@PathVariable Long stickerSeq) {
		stickerService.deleteSticker(stickerSeq);
		return responseService.getSuccessResult();
	}
}
