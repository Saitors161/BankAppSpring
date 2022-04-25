package com.example.bank_app.service.implementation;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.bank_app.error.UserEntityNotFoundException;
import com.example.bank_app.model.Role;
import com.example.bank_app.model.Status;
import com.example.bank_app.model.User;
import com.example.bank_app.repository.UserRepository;

public class UserServiceImplementationTest {

	@Test
	void testFindByNullUsername() {
		
		UserRepository repository = Mockito.mock(UserRepository.class);
		Mockito.when(repository.findByUsername(null)).thenReturn(null);
		UserServiceImplementation userServiceImplementation = new UserServiceImplementation(repository, null);
		Assertions.assertThrows(UserEntityNotFoundException.class, () -> {
			userServiceImplementation.findByUsername(null);
		});
	}
	
	@Test
	void testFindByEmptyUsername() {
		
		UserRepository repository = Mockito.mock(UserRepository.class);
		Mockito.when(repository.findByUsername("")).thenReturn(null);
		UserServiceImplementation userServiceImplementation = new UserServiceImplementation(repository, null);
		Assertions.assertThrows(UserEntityNotFoundException.class, () -> {
			userServiceImplementation.findByUsername("");
		});
	}
	
	@Test
	void testFindByCorrectUsername() {
		User user = testUser();
		UserRepository repository = Mockito.mock(UserRepository.class);
		Mockito.when(repository.findByUsername("sale")).thenReturn(user);
		UserServiceImplementation userServiceImplementation = new UserServiceImplementation(repository, null);
		Assertions.assertEquals(user, userServiceImplementation.findByUsername("sale"));;
	}

	
	User testUser () {
		User user = new User();
		user.setEmail("sale@palala.ru");
		user.setFirstName("Nikola");
		user.setLastName("Tesla");
		user.setId((long) 4);
		user.setUsername("NicolaTesla");
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setStatus(Status.ACTIVE);
		Role role = testRole(user);
		user.setRoles(List.of(role));
		return user;
	}
	
	Role testRole(User user) {
		Role role = new Role();
		role.setId((long) 1);
		role.setCreated(new Date());
		role.setName("ADMIN1");
		role.setStatus(Status.ACTIVE);
		role.setUpdated(new Date());
		role.setUsers(List.of(user));
		return role;
	}
}
