package org.diamond_badge.footprint.jpa.repo;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.Report;
import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
	Report findByUserAndDiary(User user, Diary diary);

}
