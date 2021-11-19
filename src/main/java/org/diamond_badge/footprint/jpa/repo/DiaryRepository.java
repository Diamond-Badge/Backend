package org.diamond_badge.footprint.jpa.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary,Long> {

	Diary findDiaryByDiarySeq(Long diarySeq);
	List<Diary> findByTimeLine(TimeLine timeLine);
	List<Diary> findDiariesByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
	List<Diary> findByCreatedAtBetweenAndUserEmail(LocalDateTime start, LocalDateTime end,String userEmail);

}
