package com.example.bank_app.dto;

import com.example.bank_app.model.Profile;
import com.example.bank_app.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDTO {
	private Long id;
	private Long cash;
	private User user;
	
	public Profile toProfile() {
		Profile profile = new Profile();
		profile.setId(id);
		profile.setCash(cash);;
		profile.setUser(user);
		return profile;
	}
	
	public static ProfileDTO fromProfile(Profile profile) {
		return new ProfileDTO(profile.getId()
											  ,profile.getCash()
											  ,profile.getUser());	
	}
	
}