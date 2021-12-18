package org.diamond_badge.footprint.jpa.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	@Query("select count(diarySeq) from Diary where userEmail=:userEmail and createdAt between :start and :end")
	Optional<Integer> getDiaryCountByCreatedAtBetweenAndUserEmail(LocalDateTime start, LocalDateTime end,
		String userEmail);

	@Query(value = "select DIARY_LOCATION FROM DIARY WHERE USER_NAME=:userEmail and CREATED_AT between :start and :end GROUP BY DIARY_LOCATION ORDER BY COUNT(DIARY_SEQ) DESC Limit 3", nativeQuery = true)
	List<String> getLocationCountByCreatedAtBetweenAndUserEmail(LocalDateTime start, LocalDateTime end,
		String userEmail);

	Diary findDiaryByDiarySeq(Long diarySeq);

	List<Diary> findByTimeLine(TimeLine timeLine);

	List<Diary> findDiariesByCreatedAtBetweenAndIsWrittenIsTrue(LocalDateTime start, LocalDateTime end);

	List<Diary> findByCreatedAtBetweenAndUserEmail(LocalDateTime start, LocalDateTime end, String userEmail);

}
