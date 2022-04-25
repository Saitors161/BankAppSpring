package com.example.bank_app.service;

import com.example.bank_app.model.Currency;
import com.example.bank_app.model.Profile;
import com.example.bank_app.model.User;

public interface ProfileService {
	
	Profile createProfile(User user,Currency currency);

	Profile findProfile(User user, Long numberProfile);
	
	Profile findProfile(Long numberProfile);

	void deposit(Profile profile, Long deposit);

	void withdraw(Profile profile, Long value, Long numberProfileWithdraw);
	
	User findByProfileNumber(Long profileNumber);
	
	void sendToOtherUser(Long senderProfileNumber, Long receiverProfileNumber, Long size);

	Long closeProfile( Long numberProfile);



}
