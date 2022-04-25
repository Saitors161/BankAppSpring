package com.example.bank_app.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.bank_app.model.Role;
import com.example.bank_app.model.Status;
import com.example.bank_app.model.User;

public final class JwtUserFactory {

	public JwtUserFactory() {
	}
	
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(),
						   user.getUsername(),
						   user.getFirstName(),
						   user.getLastName(),
						   user.getEmail(),
						   user.getPassword(),
						   mapToGrantedAuthorities(new ArrayList<Role>(user.getRoles())),
						   user.getStatus().equals(Status.ACTIVE),
						   user.getUpdated());
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
		return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())
		).collect(Collectors.toList());
		
	}
	
	
}
