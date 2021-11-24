package org.diamond_badge.footprint.jpa.repo;

import java.util.Optional;

import org.diamond_badge.footprint.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}

