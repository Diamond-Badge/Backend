package org.diamond_badge.footprint.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.EmotionType;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.DiaryRepository;
import org.diamond_badge.footprint.jpa.repo.TimeLineRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.diamond_badge.footprint.vo.DiaryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final TimeLineRepository timeLineRepository;
	private final UserRepository userRepository;

	//한달 단위 내 일기 불러오기
	public List<Diary> findDiarysByMonth(String email,LocalDateTime dateTime){
		YearMonth yearMonth=YearMonth.from(dateTime);
		LocalDateTime startDatetime = LocalDateTime.of(yearMonth.getYear(),yearMonth.getMonthValue(),1,0,0,0);
		LocalDateTime endDatetime = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.of(23,59,59));
		return diaryRepository.findByCreatedAtBetweenAndUserEmail(startDatetime,endDatetime,email);
	}

	//주 단위 내 일기 불러오기
	public List<Diary> findDiarysByWeek(String email,LocalDateTime dateTime){
		DayOfWeek dayOfWeek= dateTime.getDayOfWeek();
		int day=dayOfWeek.get(ChronoField.DAY_OF_WEEK);
		LocalDateTime startDatetime = LocalDateTime.of(dateTime.minusDays(day-1).getYear(),dateTime.minusDays(day-1).getMonthValue(),dateTime.minusDays(day-1).getDayOfMonth(),0,0,0);
		LocalDateTime endDatetime = LocalDateTime.of(dateTime.plusDays(7-day).getYear(),dateTime.plusDays(7-day).getMonthValue(),dateTime.plusDays(7-day).getDayOfMonth(),23,59,59);
		return diaryRepository.findByCreatedAtBetweenAndUserEmail(startDatetime,endDatetime,email);
	}

	//하루단위 내 일기 불러오기
	public List<Diary> findDiarysByDay(String email,LocalDateTime dateTime){
		LocalDateTime startDatetime = LocalDateTime.of(dateTime.toLocalDate(),LocalTime.of(0,0,0));
		LocalDateTime endDatetime = LocalDateTime.of(dateTime.toLocalDate(),LocalTime.of(23,59,59));
		return diaryRepository.findByCreatedAtBetweenAndUserEmail(startDatetime,endDatetime,email);
	}
	//전체조회(지도 전송 오전 6시부터 다음날 오전 6시까지)
	public List<Diary> findDiarys(){
		LocalDateTime localDateTime=LocalDateTime.now();
		LocalDateTime startDatetime = LocalDateTime.of(localDateTime.minusDays(1).toLocalDate(),LocalTime.of(6,0,0));
		LocalDateTime endDatetime = LocalDateTime.of(localDateTime.toLocalDate(),LocalTime.of(6,0,0));
		return diaryRepository.findDiariesByCreatedAtBetween(startDatetime,endDatetime);
	}

	//위치 일기 단건 조회
	public Diary findDiary(Long diarySeq){
		return diaryRepository.findById(diarySeq).orElseThrow();
	}


	//위치 등록 -> 처음 등록이면 timeLine 자동생성
	@Transactional
	public Diary createDiary(DiaryRequest diaryRequest, String email){
		LocalDateTime now=LocalDateTime.now();
		User user= userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		Diary diary=new Diary(diaryRequest.getLocation(), diaryRequest.getLatitude(), diaryRequest.getLongtitude(),now,now,user.getUsername(),
			user.getEmail());
		LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); //오늘 00:00:00
		LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59
		Optional<TimeLine> timeLine = timeLineRepository.findTimeLineByCreatedAtBetweenAndUser(startDatetime,endDatetime,user);
		if(!timeLine.isPresent()) {
			TimeLine newTimeLine = new TimeLine(EmotionType.DEFAULT, now,user);
			timeLineRepository.save(newTimeLine);
			user.addTimeLine(newTimeLine);
			newTimeLine.newDiary(diary);
			diary.setTimeLine(newTimeLine);
		}else {
			timeLine.get().newDiary(diary);
			diary.setTimeLine(timeLine.get());
		}
		return diaryRepository.save(diary);
	}
	//글 내용 삽입
	@Transactional
	public Diary updateDiary(String content,String place,Long diarySeq){
		Diary diary=diaryRepository.findById(diarySeq).orElseThrow();
		diary.setContent(content);
		diary.setPalce(place);
		return diary;
	}

	//위치 삭제
	@Transactional
	public boolean deleteDiary(Long diarySeq){
		diaryRepository.deleteById(diarySeq);
		return true;
	}


	//좋아요기능
}
