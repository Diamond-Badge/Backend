package org.diamond_badge.footprint.jpa.repo;

import org.diamond_badge.footprint.jpa.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
	UserRefreshToken findByUserEmail(String userEmail);

	UserRefreshToken findByUserEmailAndRefreshToken(String userEmail, String refreshToken);

	void deleteByUserEmail(String email);
}
