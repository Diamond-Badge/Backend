package org.diamond_badge.footprint.jpa.repo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeLineRepository extends JpaRepository<TimeLine,Long> {

	Optional<TimeLine> findTimeLineByCreatedAtBetweenAndUser(LocalDateTime start, LocalDateTime end, User user);

}
