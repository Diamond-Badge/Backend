package org.diamond_badge.footprint.jpa.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	@Query("select count(diarySeq) from Diary where userEmail=:userEmail and createdAt between :start and :end")
	int getDiaryCountByCreatedAtBetweenAndUserEmail(LocalDateTime start, LocalDateTime end, String userEmail);

	Diary findDiaryByDiarySeq(Long diarySeq);

	List<Diary> findByTimeLine(TimeLine timeLine);

	List<Diary> findDiariesByCreatedAtBetweenAndIsWrittenIsTrue(LocalDateTime start, LocalDateTime end);

	List<Diary> findByCreatedAtBetweenAndUserEmail(LocalDateTime start, LocalDateTime end, String userEmail);

}
