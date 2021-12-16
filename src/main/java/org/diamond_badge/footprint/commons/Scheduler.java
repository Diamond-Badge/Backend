package org.diamond_badge.footprint.commons;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import org.diamond_badge.footprint.jpa.entity.Statistics;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.DiaryRepository;
import org.diamond_badge.footprint.jpa.repo.StatisticsRepository;
import org.diamond_badge.footprint.jpa.repo.TimeLineRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Scheduler {

	private final DiaryRepository diaryRepository;
	private final TimeLineRepository timeLineRepository;
	private final UserRepository userRepository;
	private final StatisticsRepository statisticsRepository;

	//사용자마다 나눠서 숫자 세야함 and 사용자 전체적으로 repo 업데이트 해야함
	@Scheduled(cron = "0 0 0 1 * ?")
	public void statisticsSchedule() {
		LocalDateTime dateTime1 = LocalDateTime.now().minusMonths(1);
		YearMonth yearMonth1 = YearMonth.from(dateTime1);
		LocalDateTime startDatetime1 = LocalDateTime.of(yearMonth1.getYear(), yearMonth1.getMonthValue(), 1, 0, 0, 0);
		LocalDateTime endDatetime1 = LocalDateTime.of(yearMonth1.atEndOfMonth(), LocalTime.of(23, 59, 59));

		LocalDateTime dateTime2 = LocalDateTime.now().minusMonths(2);
		YearMonth yearMonth2 = YearMonth.from(dateTime2);
		LocalDateTime startDatetime2 = LocalDateTime.of(yearMonth2.getYear(), yearMonth2.getMonthValue(), 1, 0, 0, 0);
		LocalDateTime endDatetime2 = LocalDateTime.of(yearMonth2.atEndOfMonth(), LocalTime.of(23, 59, 59));

		List<User> userList = userRepository.findAll();

		userList.forEach(user -> {

				int agoDiaryCnt = diaryRepository.getDiaryCountByCreatedAtBetweenAndUserEmail(
					startDatetime2,
					endDatetime2,
					user.getEmail());
				int nextDiaryCnt = diaryRepository.getDiaryCountByCreatedAtBetweenAndUserEmail(
					startDatetime1,
					endDatetime1,
					user.getEmail());

				int agoTimeLineCnt = timeLineRepository.getTimeLineCountByCreatedAtBetweenAndUser(
					startDatetime2,
					endDatetime2,
					user);
				int nextTimeLineCnt = timeLineRepository.getTimeLineCountByCreatedAtBetweenAndUser(
					startDatetime1,
					endDatetime1,
					user);

				Statistics statistics = statisticsRepository.findByUser(user);
				statistics.setActive(nextTimeLineCnt - agoTimeLineCnt, nextDiaryCnt - agoDiaryCnt);

			}
		);

	}

}
