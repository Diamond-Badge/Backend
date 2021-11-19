package org.diamond_badge.footprint.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.ProviderType;
import org.diamond_badge.footprint.jpa.entity.RoleType;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.diamond_badge.footprint.model.social.KakaoProfile;
import org.diamond_badge.footprint.model.social.NaverProfile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final KakaoService kakaoService;
	private final NaverService naverService;

	public User signupByKakao(String accessToken, String provider) {
		KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
		KakaoProfile.Kakao_account kakaoAccount = profile.getKakao_account();
		System.out.println(String.valueOf(kakaoAccount.getEmail()));
		Optional<User> user = userRepository.findByEmail(String.valueOf(kakaoAccount.getEmail()));
		LocalDateTime now=LocalDateTime.now();
		System.out.println(user.isPresent());
		if (user.isPresent()) {
			return user.get();
		}else {
			System.out.println(kakaoAccount.getNickname()+" "+ kakaoAccount.getEmail());
			User kakaoUser=new User(kakaoAccount.getEmail(), kakaoAccount.getEmail(),
				null, ProviderType.KAKAO, RoleType.USER,now,now);

			return userRepository.save(kakaoUser);
		}
	}


	public User signupByNaver(String accessToken, String provider) {
		System.out.print(accessToken);
		NaverProfile naverProfile = naverService.getNaverProfile(accessToken);
		NaverProfile.Response naverAccount = naverProfile.getResponse();
		System.out.print(naverAccount.getEmail());
		LocalDateTime now=LocalDateTime.now();
		Optional<User> user = userRepository.findByEmail(naverAccount.getEmail());
		if (user.isPresent()) {
			return user.get();
		} else {

			User naverUser=new User(naverAccount.getId(), naverAccount.getEmail(),null
				, ProviderType.NAVER, RoleType.USER,now,now);

			userRepository.save(naverUser);

			return naverUser;
		}
	}



}
