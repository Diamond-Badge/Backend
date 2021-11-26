package org.diamond_badge.footprint.jpa.repo;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.Like;
import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Like findByUserAndDiary(User user, Diary diary);

}
