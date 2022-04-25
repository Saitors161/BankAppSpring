package com.example.bank_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bank_app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String name);

	Optional<User> findByEmail(String email);

}
