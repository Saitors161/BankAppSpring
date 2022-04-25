package com.example.bank_app.service;

import java.util.List;

import com.example.bank_app.model.User;


public interface UserService {
	
	User register(User user);
	
	List<User> getAll();
	
	User findByUsername(String username);

	User findById(Long id);
	
	User findByEmail(String email);

	User updateUser(User user);

	Long deleteUser(Long id);	

	List<User> findByFirstName(String string);

	List<User> findByLastName(String string);
	
}
