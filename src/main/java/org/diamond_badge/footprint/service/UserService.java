package org.diamond_badge.footprint.service;

import java.util.Optional;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.ProviderType;
import org.diamond_badge.footprint.jpa.entity.RoleType;
import org.diamond_badge.footprint.jpa.entity.Statistics;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.StatisticsRepository;
import org.diamond_badge.footprint.jpa.repo.UserRefreshTokenRepository;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.diamond_badge.footprint.model.social.GoogleProfile;
import org.diamond_badge.footprint.model.social.KakaoProfile;
import org.diamond_badge.footprint.model.social.NaverProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final StatisticsRepository statisticsRepository;
	private final UserRefreshTokenRepository userRefreshTokenRepository;
	private final KakaoService kakaoService;
	private final NaverService naverService;
	private final GoogleService googleService;

	public User signupByKakao(String accessToken, String provider) {
		KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
		KakaoProfile.Kakao_account kakaoAccount = profile.getKakao_account();
		System.out.println(String.valueOf(kakaoAccount.getEmail()));
		Optional<User> user = userRepository.findByEmail(String.valueOf(kakaoAccount.getEmail()));
		System.out.println(user.isPresent());
		if (user.isPresent()) {
			return user.get();
		} else {
			System.out.println(kakaoAccount.getNickname() + " " + kakaoAccount.getEmail());
			User kakaoUser = new User(kakaoAccount.getEmail(), kakaoAccount.getEmail(),
				null, ProviderType.KAKAO, RoleType.USER);
			userRepository.save(kakaoUser);
			Statistics statistics = new Statistics(kakaoAccount.getEmail());
			statisticsRepository.save(statistics);

			return kakaoUser;
		}
	}

	public User signupByNaver(String accessToken, String provider) {
		System.out.print(accessToken);
		NaverProfile naverProfile = naverService.getNaverProfile(accessToken);
		NaverProfile.Response naverAccount = naverProfile.getResponse();
		System.out.print(naverAccount.getEmail());
		Optional<User> user = userRepository.findByEmail(naverAccount.getEmail());
		if (user.isPresent()) {
			return user.get();
		} else {

			User naverUser = new User(naverAccount.getId(), naverAccount.getEmail(), null
				, ProviderType.NAVER, RoleType.USER);

			userRepository.save(naverUser);
			Statistics statistics = new Statistics(naverAccount.getId());
			statisticsRepository.save(statistics);
			return naverUser;
		}
	}

	public User signupByGoogle(String accessToken, String provider) throws Exception {
		System.out.print(accessToken);
		GoogleProfile googleProfile = googleService.getGoogleUserInfo(accessToken);
		System.out.print(googleProfile.getEmail());
		Optional<User> user = userRepository.findByEmail(googleProfile.getEmail());
		if (user.isPresent()) {
			return user.get();
		} else {
			User googleUser = new User(googleProfile.getId(), googleProfile.getEmail(), null
				, ProviderType.GOOGLE, RoleType.USER);

			userRepository.save(googleUser);
			Statistics statistics = new Statistics(googleProfile.getId());
			statisticsRepository.save(statistics);
			return googleUser;
		}
	}

	public User getUser(String email) {
		return userRepository.findByEmail(email).orElseThrow();
	}

	//????????? ??????
	public boolean setUsername(String email, String name) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		user.setUsername(name);
		return true;
	}

	//????????? ?????? ??????
	public void setProfileUrl(String email, String profilePath) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		user.setProfileImageUrl(profilePath);
	}

	// ????????? ?????? ??????
	public void setIsPublic(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		user.setPrivate();
	}

	//?????? ??????
	public void deleteUser(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		userRepository.delete(user);
		userRefreshTokenRepository.deleteByUserEmail(email);
	}

}
