package org.diamond_badge.footprint.service;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.Like;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.DiaryRepository;
import org.diamond_badge.footprint.jpa.repo.LikeRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final DiaryRepository diaryRepository;

	@Transactional
	public boolean likes(String email, Long diarySeq) {
		Diary diary = diaryRepository.findDiaryByDiarySeq(diarySeq);
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		Like like = likeRepository.findByUserAndDiary(user, diary);
		if (like == null) {
			Like newLike = new Like(user, diary);
			newLike = likeRepository.save(newLike);
			diary.updateLikeCount();
			diaryRepository.save(diary);
			return true;
		} else {
			likeRepository.deleteById(like.getLikeSeq());
			diary.cancleLike(like);
			diary.updateLikeCount();
			return false;
		}
	}

}
