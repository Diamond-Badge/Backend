package org.diamond_badge.footprint.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

	public boolean addLike(String email, Long diarySeq){
		Diary diary=diaryRepository.findDiaryByDiarySeq(diarySeq);
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		if(isNotAlreadyLike(user,diary)){
			likeRepository.save(new Like(user,diary));
			return true;
		}else{
			return false;
		}
	}

	public void cancelLike(String email, Long diarySeq){
		Diary diary=diaryRepository.findDiaryByDiarySeq(diarySeq);
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		Like like = likeRepository.findByUserAndDiary(user, diary).orElseThrow();
		likeRepository.delete(like);
	}

	public List<String> count(String email, Long diarySeq){
		Diary diary=diaryRepository.findDiaryByDiarySeq(diarySeq);
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

		Integer diaryLikeCount = likeRepository.countByDiary(diary).orElse(0);
		List<String> resultData =
			new ArrayList<>(Arrays.asList(String.valueOf(diaryLikeCount)));

		if (Objects.nonNull(user)) {
			resultData.add(String.valueOf(isNotAlreadyLike(user, diary)));
			return resultData;
		}

		return resultData;
	}

	public boolean isNotAlreadyLike(User user,Diary diary){
		return likeRepository.findByUserAndDiary(user,diary).isEmpty();
	}

}
