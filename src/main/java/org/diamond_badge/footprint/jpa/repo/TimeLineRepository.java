package org.diamond_badge.footprint.jpa.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.EmotionType;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeLineRepository extends JpaRepository<TimeLine, Long> {

	@Query("select count(timeLineSeq) from TimeLine where user=:user and createdAt between :start and :end")
	int getTimeLineCountByCreatedAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

	@Query("select count(timeLineSeq) from TimeLine where user=:user and emotionType=:emotionType and createdAt between :start and :end")
	int getEmotionCountByCreatedAtBetweenAndUserAndEmotionType(LocalDateTime start, LocalDateTime end, User user,
		EmotionType emotionType);

	Optional<TimeLine> findTimeLineByCreatedAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

	List<TimeLine> findTimeLinesByCreatedAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

}
