package org.diamond_badge.footprint.jpa.repo;

import org.diamond_badge.footprint.jpa.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
	Statistics findByUserEmail(String email);
}
