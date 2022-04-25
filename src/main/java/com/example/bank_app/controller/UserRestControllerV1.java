package com.example.bank_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.bank_app.dto.UserDTO;
import com.example.bank_app.error.ApiError;
import com.example.bank_app.error.UserEntityNotFoundException;
import com.example.bank_app.model.Currency;
import com.example.bank_app.model.Profile;
import com.example.bank_app.model.User;
import com.example.bank_app.service.ProfileService;
import com.example.bank_app.service.UserService;

@RestController
@RequestMapping("/api/v1/")
public class UserRestControllerV1 {

	private final UserService userService;
	private final ProfileService profileService;
	
	public UserRestControllerV1(UserService userService,ProfileService profileService) {
		this.userService = userService;
		this.profileService = profileService;

	}
	
	@GetMapping(value = "users")
	public ResponseEntity<UserDTO> getUser(@RequestParam(name = "username") Optional <String> username) {
		
		User user = null;
		if (username.isPresent()&& username.get().equals(getCurrentUsername())) {
			user = userService.findByUsername(username.get());
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (user==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(UserDTO.fromUser(user),HttpStatus.OK);
	}
	
	@PutMapping(value = "users")
	public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {	
		user = this.userService.updateUser(user);
		if (user==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(UserDTO.fromUser(user),HttpStatus.OK); 
	}

	@DeleteMapping(path = "users")
	public void deleteUser(@RequestParam Long id) {		
		this.userService.deleteUser(id);
	}
	
	@PostMapping(value = "users/{id}/profile/{currency}") 
	public ResponseEntity<UserDTO> createProfile(@PathVariable Long id, @PathVariable Currency currency ) {
		User user = null;
		Profile profile = null;
		if ( id!= 0 ) {
			user = userService.findById(id);
			if (user!=null) {
				profile = profileService.createProfile(user,currency);	
			}		
		}
		
		if (user == null || profile == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(UserDTO.fromUser(user),HttpStatus.OK); 
		
		
	}
	
	@DeleteMapping(value = "users/{id}/profile/{numberProfile}") 
	public ResponseEntity<Long> deleteProfile(@PathVariable Long id, @PathVariable Long numberProfile ) {
		User user = null;
		if ( id!= 0 ) {
			user = userService.findById(id);
			if (user!=null) {
				numberProfile = profileService.closeProfile(numberProfile);	
			}		
		}
		
		if (user==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}	
		return new ResponseEntity<>(numberProfile,HttpStatus.OK);
	}
	
	
	@PostMapping(value = "users/{id}/profile/{numberProfile}/deposit/{value}") 
	public ResponseEntity<Long> deposit(@PathVariable Long id, @PathVariable Long numberProfile,@PathVariable Long value) {
		User user = null;
		Profile profile = null;
		if ( id!= 0 ) {
			user = userService.findById(id);
			if (user!=null) {
				profile = profileService.findProfile(user,numberProfile);
				if (profile !=null) {
					profileService.deposit(profile,value);
				}
			}		
		}
		
		if (user==null||profile==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}	
		return new ResponseEntity<>(numberProfile,HttpStatus.OK);
	}
	
	@PostMapping(value = "users/{id}/profile/{numberProfile}/withdraw/{value}") 
	public ResponseEntity<Long> withdraw (@PathVariable Long id, @PathVariable Long numberProfile,
										  @PathVariable Long value, @RequestParam (name = "numberProfileWithdraw") Long numberProfileWithdraw) {
		User user = null;
		Profile profile = null;
		if ( id!= 0 ) {
			user = userService.findById(id);
			if (user!=null) {
				profile = profileService.findProfile(user,numberProfile);
				if (profile !=null) {
					profileService.withdraw(profile,value,numberProfileWithdraw);
				}
			}		
		}
		
		if (user==null||profile==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}	
		return new ResponseEntity<>(numberProfile,HttpStatus.OK);
	}
	
	public String getCurrentUsername() {
	      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      return auth.getName();
	  }
	
	@ControllerAdvice
	public class RestExceptionHandler {
	    @ExceptionHandler({UserEntityNotFoundException.class, EntityNotFoundException.class})
	    protected ResponseEntity<Object> handleEntityNotFoundEx(RuntimeException ex, WebRequest request) {
	      ApiError apiError = new ApiError("entity not found ex", ex.getMessage());
	      return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	    }
	}
}
