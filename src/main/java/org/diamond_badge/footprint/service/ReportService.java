package org.diamond_badge.footprint.service;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.Report;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.DiaryRepository;
import org.diamond_badge.footprint.jpa.repo.ReportRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final DiaryRepository diaryRepository;

	@Transactional
	public boolean reports(String email, Long diarySeq, String reason) {
		Diary diary = diaryRepository.findDiaryByDiarySeq(diarySeq);
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		Report report = reportRepository.findByUserAndDiary(user, diary);
		if (report == null) {
			Report newReport = new Report(user, diary, reason);
			newReport = reportRepository.save(newReport);
			diary.plusReport();
			diaryRepository.save(diary);
			return true;
		}
		return false;

	}

}
