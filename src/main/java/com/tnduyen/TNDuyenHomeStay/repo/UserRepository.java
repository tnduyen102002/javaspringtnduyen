package com.tnduyen.TNDuyenHomeStay.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tnduyen.TNDuyenHomeStay.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
