package org.diamond_badge.footprint.commons;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import org.diamond_badge.footprint.jpa.entity.EmotionType;
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
	//@Scheduled(cron = "10 * * * * *") 테스트용
	@Scheduled(cron = "0 0 0 1 * ?")
	public void statisticsSchedule() {
		LocalDateTime dateTime1 = LocalDateTime.now().minusMonths(1);//LocalDateTime dateTime1 = LocalDateTime.now(); 테스트용
		YearMonth yearMonth1 = YearMonth.from(dateTime1);
		LocalDateTime startDatetime1 = LocalDateTime.of(yearMonth1.getYear(), yearMonth1.getMonthValue(), 1, 0, 0, 0);
		LocalDateTime endDatetime1 = LocalDateTime.of(yearMonth1.atEndOfMonth(), LocalTime.of(23, 59, 59));

		LocalDateTime dateTime2 = LocalDateTime.now().minusMonths(2);
		YearMonth yearMonth2 = YearMonth.from(dateTime2);
		LocalDateTime startDatetime2 = LocalDateTime.of(yearMonth2.getYear(), yearMonth2.getMonthValue(), 1, 0, 0, 0);
		LocalDateTime endDatetime2 = LocalDateTime.of(yearMonth2.atEndOfMonth(), LocalTime.of(23, 59, 59));

		List<User> userList = userRepository.findAll();

		System.out.println(userList.isEmpty());
		System.out.println(userList.size());

		userList.forEach(user -> {

				int agoDiaryCnt = diaryRepository.getDiaryCountByCreatedAtBetweenAndUserEmail(
					startDatetime2,
					endDatetime2,
					user.getEmail()).orElse(0);
				int nextDiaryCnt = diaryRepository.getDiaryCountByCreatedAtBetweenAndUserEmail(
					startDatetime1,
					endDatetime1,
					user.getEmail()).orElse(0);

				int agoTimeLineCnt = timeLineRepository.getTimeLineCountByCreatedAtBetweenAndUser(
					startDatetime2,
					endDatetime2,
					user).orElse(0);
				int nextTimeLineCnt = timeLineRepository.getTimeLineCountByCreatedAtBetweenAndUser(
					startDatetime1,
					endDatetime1,
					user).orElse(0);

				int HAPPY = timeLineRepository.getEmotionCountByCreatedAtBetweenAndUserAndEmotionType(startDatetime1,
					endDatetime1, user,
					EmotionType.HAPPY).orElse(0);
				int EXCITED = timeLineRepository.getEmotionCountByCreatedAtBetweenAndUserAndEmotionType(startDatetime1,
					endDatetime1, user,
					EmotionType.EXCITED).orElse(0);
				int ANGRY = timeLineRepository.getEmotionCountByCreatedAtBetweenAndUserAndEmotionType(startDatetime1,
					endDatetime1, user,
					EmotionType.ANGRY).orElse(0);
				int SAD = timeLineRepository.getEmotionCountByCreatedAtBetweenAndUserAndEmotionType(startDatetime1,
					endDatetime1, user,
					EmotionType.SAD).orElse(0);
				int DEPRESSED = timeLineRepository.getEmotionCountByCreatedAtBetweenAndUserAndEmotionType(startDatetime1,
					endDatetime1, user,
					EmotionType.DEPRESSED).orElse(0);

				int total = HAPPY + EXCITED + ANGRY + SAD + DEPRESSED;

				List<String> locations = diaryRepository.getLocationCountByCreatedAtBetweenAndUserEmail(startDatetime1,
					endDatetime1, user.getEmail());

				System.out.println(locations.get(0) + " " + locations.get(1));

				Statistics statistics = statisticsRepository.findByUserEmail(user.getEmail());
				statistics.setActive(nextTimeLineCnt - agoTimeLineCnt, nextDiaryCnt - agoDiaryCnt);
				if (total != 0) {
					statistics.setEmotion(EXCITED / total, HAPPY / total, SAD / total, ANGRY / total, DEPRESSED / total);
				}
				switch (locations.size()) {
					case 0:
						break;
					case 1:
						statistics.setLocation(locations.get(0), "없습니다", "없습니다");
						break;
					case 2:
						statistics.setLocation(locations.get(0), locations.get(1), "없습니다");
						break;
					case 3:
						statistics.setLocation(locations.get(0), locations.get(1), locations.get(2));
						break;
				}
				statisticsRepository.save(statistics);
			}
		);

	}
}
