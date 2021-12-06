package org.diamond_badge.footprint.service;

import java.util.List;

import org.diamond_badge.footprint.jpa.entity.Sticker;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.jpa.repo.StickerRepository;
import org.diamond_badge.footprint.jpa.repo.TimeLineRepository;
import org.diamond_badge.footprint.model.vo.StickerRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StickerService {

	private final TimeLineRepository timeLineRepository;
	private final StickerRepository stickerRepository;

	public List<Sticker> findStickers(long timeLineSeq) {
		TimeLine timeLine = timeLineRepository.findById(timeLineSeq).orElseThrow();
		return stickerRepository.findStickersByTimeLineOrderByModifiedAtAsc(timeLine);
	}

	@Transactional
	public Sticker registerSticker(StickerRequest stickerRequest, long timeLineSeq) {
		TimeLine timeLine = timeLineRepository.findById(timeLineSeq).orElseThrow();

		Sticker sticker = Sticker.builder()
			.x(stickerRequest.getX())
			.y(stickerRequest.getY())
			.stickerType(stickerRequest.getStickerType())
			.timeLine(timeLine)
			.build();

		return stickerRepository.save(sticker);

	}

	@Transactional
	public void deleteSticker(long stickerSeq) {
		stickerRepository.deleteById(stickerSeq);
	}

}
