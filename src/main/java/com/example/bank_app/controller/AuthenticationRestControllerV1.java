package com.example.bank_app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank_app.dto.AuthenticationRequestDTO;
import com.example.bank_app.model.User;
import com.example.bank_app.security.jwt.JwtTokenProvider;
import com.example.bank_app.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	private final UserService userService;

	@Autowired
	public AuthenticationRestControllerV1(AuthenticationManager authenticationManager,
			JwtTokenProvider jwtTokenProvider, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}
	
	@PostMapping(path = "login")
	public ResponseEntity login(@RequestBody AuthenticationRequestDTO requestDTO) {
		try {
			String username = requestDTO.getUsername();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));
			User user = userService.findByUsername(username);
			
			if(user == null) {
				throw new UsernameNotFoundException("User with username:" + username + " not found");
			}
			String token = jwtTokenProvider.createToken(username,user.getRoles());
			Map<Object,Object> response = new HashMap();
			response.put("username", username);
			response.put("token", token);
			
			return ResponseEntity.ok(response);			
			
		}catch(UsernameNotFoundException e) {
			throw new BadCredentialsException("Invalid username or bad password");
		}
	}
	
	
	

}
