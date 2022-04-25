package com.example.bank_app.service.implementation;

import org.springframework.stereotype.Service;

import com.example.bank_app.model.Currency;
import com.example.bank_app.model.Profile;
import com.example.bank_app.model.User;
import com.example.bank_app.service.ProfileService;

@Service
public class ProfileServiceImplementation implements ProfileService {

	@Override
	public Profile createProfile(User user, Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile findProfile(User user, Long numberProfile) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Profile findProfile(Long numberProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deposit(Profile profile, Long deposit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdraw(Profile profile, Long value, Long numberProfileWithdraw) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Long closeProfile(Long numberProfile) {	
		// TODO Auto-generated method stub
		return numberProfile;
	}

	@Override
	public User findByProfileNumber(Long profileNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendToOtherUser(Long senderProfileNumber, Long receiverProfileNumber, Long size) {
		// TODO Auto-generated method stub
		
	}
	


}
