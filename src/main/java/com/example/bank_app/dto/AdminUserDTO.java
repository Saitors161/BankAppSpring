package com.example.bank_app.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.bank_app.model.Status;
import com.example.bank_app.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDTO {

	private Long id;
	
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Status status;
	
	public User toUser() {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setStatus(status);
		return user;
	}
	
	public static AdminUserDTO fromUser(User user) {
		AdminUserDTO userDTO = new AdminUserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setStatus(user.getStatus());		
		return userDTO;	
	}
	
	public static List<AdminUserDTO> fromUser(List<User> users) {
		List<AdminUserDTO> listAdminUserDTO = new ArrayList<AdminUserDTO>();
		users.forEach(user -> listAdminUserDTO.add(fromUser(user)));	
		return listAdminUserDTO;	
	}

}
