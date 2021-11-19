package org.diamond_badge.footprint.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.List;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.Diary;
import org.diamond_badge.footprint.jpa.entity.TimeLine;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.TimeLineRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeLineService {
	private final TimeLineRepository timeLineRepository;
	private final UserRepository userRepository;

	//단건조회
	public TimeLine getTimeLine(String email,LocalDate localDate){
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		return timeLineRepository.findTimeLineByCreatedAtContainsAndUser(localDate,user).orElseThrow();
	}

	//한달 단위 내 타임라인 불러오기
	public List<TimeLine> findTimeLinesByMonth(String email,LocalDate dateTime){
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		YearMonth yearMonth=YearMonth.from(dateTime);
		LocalDateTime startDatetime = LocalDateTime.of(yearMonth.getYear(),yearMonth.getMonthValue(),1,0,0,0);
		LocalDateTime endDatetime = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.of(23,59,59));
		return timeLineRepository.findTimeLinesByCreatedAtBetweenAndUser(startDatetime,endDatetime,user);
	}

	//주 단위 내 타임라인 불러오기
	public List<TimeLine> findTimeLinesByWeek(String email,LocalDate dateTime){
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		DayOfWeek dayOfWeek= dateTime.getDayOfWeek();
		int day=dayOfWeek.get(ChronoField.DAY_OF_WEEK);
		LocalDateTime startDatetime = LocalDateTime.of(dateTime.minusDays(day-1).getYear(),dateTime.minusDays(day-1).getMonthValue(),dateTime.minusDays(day-1).getDayOfMonth(),0,0,0);
		LocalDateTime endDatetime = LocalDateTime.of(dateTime.plusDays(7-day).getYear(),dateTime.plusDays(7-day).getMonthValue(),dateTime.plusDays(7-day).getDayOfMonth(),23,59,59);
		return timeLineRepository.findTimeLinesByCreatedAtBetweenAndUser(startDatetime,endDatetime,user);
	}
}
