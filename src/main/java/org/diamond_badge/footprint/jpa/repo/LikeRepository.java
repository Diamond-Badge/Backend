package org.diamond_badge.footprint.jpa.repo;

import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.Like;
import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByUserAndDiary(User user, Diary diary);

	Optional<Integer> countByDiary(Diary diary);
}
