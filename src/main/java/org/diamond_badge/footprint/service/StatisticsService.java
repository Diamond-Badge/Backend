package org.diamond_badge.footprint.service;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.Statistics;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.StatisticsRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {

	private final StatisticsRepository statisticsRepository;
	private final UserRepository userRepository;

	public Statistics getStatistics(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		return statisticsRepository.findByUserEmail(email);
	}

}
