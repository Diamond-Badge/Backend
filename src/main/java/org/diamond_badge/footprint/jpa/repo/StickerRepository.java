package org.diamond_badge.footprint.jpa.repo;

import java.util.List;

import org.diamond_badge.footprint.jpa.entity.Sticker;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
	List<Sticker> findStickersByTimeLineOrderByModifiedAtAsc(TimeLine timeLine);

}
