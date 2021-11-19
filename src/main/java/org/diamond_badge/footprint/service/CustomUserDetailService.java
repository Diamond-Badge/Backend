package org.diamond_badge.footprint.service;

import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.jpa.entity.User;
import org.diamond_badge.footprint.jpa.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) {
		//오류 있어서 수정햇는데 잘되는지 확인 필요
		User user = null;
		try {
			user = (User)userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return (UserDetails)user;
	}
}
