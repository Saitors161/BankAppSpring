package com.example.bank_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank_app.dto.AdminUserDTO;
import com.example.bank_app.model.User;
import com.example.bank_app.service.UserService;


@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {
	
	@Autowired
	private final UserService userService;	

	public AdminRestControllerV1(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(value = "users")
	public ResponseEntity<AdminUserDTO> registerUser(@RequestBody User user) {
		AdminUserDTO adminUserDTO = AdminUserDTO.fromUser(this.userService.register(user));
		if (adminUserDTO == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(adminUserDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "users")
	public ResponseEntity<List<AdminUserDTO>> getUsers(@RequestParam(name = "id") Optional <Long> id, 
													   @RequestParam(name = "username") Optional <String> username,
													   @RequestParam(name = "firstName") Optional <String> firstName,
													   @RequestParam(name = "lastName") Optional <String> lastName,
													   @RequestParam(name = "email") Optional <String> email) {
		
		// on future - maybe need create Decorator for query
		
		User user = null;
		List<User> usersList= null;
		if (id.isPresent()) {
			user = userService.findById(id.get());
			if (user==null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}			
			return new ResponseEntity<>(List.of(AdminUserDTO.fromUser(user)),HttpStatus.OK);
			
		} else if(username.isPresent()) {
			user = userService.findByUsername(username.get());
			if (user==null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(List.of(AdminUserDTO.fromUser(user)),HttpStatus.OK);
			
		} else if(firstName.isPresent()) {
			usersList = userService.findByFirstName(firstName.get());
			if (usersList==null||usersList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(AdminUserDTO.fromUser(usersList),HttpStatus.OK);
		} else if (lastName.isPresent()) {
			usersList = userService.findByLastName(lastName.get());
			if (usersList==null||usersList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(AdminUserDTO.fromUser(usersList),HttpStatus.OK);
		} else if(email.isPresent()) {
			user = userService.findByEmail(email.get());
			if (user==null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(List.of(AdminUserDTO.fromUser(user)),HttpStatus.OK);		
		} else {
			usersList = userService.getAll();
			if (usersList==null||usersList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(AdminUserDTO.fromUser(usersList),HttpStatus.OK);
		}	
	}
}
