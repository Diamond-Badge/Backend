package org.diamond_badge.footprint.jpa.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLineRepository extends JpaRepository<TimeLine, Long> {

	Optional<TimeLine> findTimeLineByCreatedAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

	List<TimeLine> findTimeLinesByCreatedAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

	Optional<TimeLine> findTimeLineByCreatedAtContainsAndUser(LocalDate time, User user);

}
