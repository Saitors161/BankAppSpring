package com.example.bank_app.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bank_app.repository.RoleRepository;
import com.example.bank_app.repository.UserRepository;
import com.example.bank_app.service.UserService;

import com.example.bank_app.error.UserEntityNotFoundException;
import com.example.bank_app.error.UserNotCreatedException;
import com.example.bank_app.model.Profile;
import com.example.bank_app.model.Role;
import com.example.bank_app.model.Status;
import com.example.bank_app.model.User;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserServiceImplementation implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public User register(User user) {
		log.info("IN register - user: {} trying registering",user);
		Role roleUser = roleRepository.findByName("ROLE_USER");
		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleUser);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);
		user.setStatus(Status.ACTIVE);

		User registeredUser = userRepository.save(user);
		if (registeredUser==null||registeredUser.getId()!=0) {
			log.info("IN register - user: {} succesfully registered",registeredUser);
			throw new UserNotCreatedException();
		}
		
		return registeredUser;
		
	}

	@Override
	public List<User> getAll() {
		log.info("IN getAll - trying to get all users");
		List<User> result = userRepository.findAll();
		log.info("IN getAll - {} users found", result.size());
		return result;
	}

	@Override
	public User findByUsername(String username) {
		
		if (username==null) {
			log.warn("IN findByUsername - incorrect username:{}", username);
			throw new UserEntityNotFoundException("username",username);
		}
		User result = userRepository.findByUsername(username);
		if(result==null) {
			log.warn("IN findByUsername - no user found by username:{}", username);
			throw new UserEntityNotFoundException("username",username);
		}
		log.info("IN findByUsername - user: {} found by username: {}", result,username);
		return result;
	}

	@Override
	public User findById(Long id) {
		User result = userRepository.findById(id).orElse(null);
		
		if(result==null) {
			log.warn("IN findById - no user found by id:{}", id);
			throw new UserEntityNotFoundException("id",id);
		}
		log.info("IN findById - user: {} found by id: {}", result,id);
		return result;
	}


	@Override
	public User findByEmail(String email) {
		User result = userRepository.findByEmail(email).orElse(null);
		
		if(result==null) {
			log.warn("IN findByEmail - no user found by email:{}", email);
			throw new UserEntityNotFoundException("email",email);
		}
		log.info("IN findByEmail - user: {} found by email: {}", result,email);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByFirstName(String firstName) {

		List<User> listResult = null;
		if (firstName != null && !firstName.isEmpty()) {
			Session session = getOpenSessionForUser();
			Query query = session.createQuery("SELECT user FROM User user  WHERE user.firstName LIKE :firstName ", User.class);
			query.setParameter("firstName", "%"+firstName+"%");
			listResult = query.getResultList();		
			session.close();
			log.info("IN findByFirstName - {} found by firstName: {}", listResult.size(), firstName);
			return listResult;

		}else {
			log.info("Doesnt get firstName for query");
			throw new UserEntityNotFoundException("first name",firstName);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByLastName(String lastName) {
		List<User> listResult = null;
		if (lastName != null && !lastName.isEmpty()) {
			Session session = getOpenSessionForUser();
			Query query = session.createQuery("SELECT user FROM User user  WHERE user.lastName LIKE :lastName", User.class);
			query.setParameter("lastName", "%"+lastName+"%");
			listResult = query.getResultList();		
			session.close();
			log.info("IN findByLastName - {} found by lastName: {}", listResult.size(), lastName);
			return listResult;

		}else {
			log.info("IN findByLastName - doesnt get lastName for query");
			throw new UserEntityNotFoundException("last name",lastName);
		}
	}

	@Override
	public User updateUser(User user) {
		log.info("IN updateUser - try update user by id:{}",user.getId());
		User userInDataBase = findById(user.getId());
		if (userInDataBase != null) {
			userInDataBase.setUsername(user.getUsername());
			userInDataBase.setFirstName(user.getFirstName());
			userInDataBase.setEmail(user.getEmail());
			this.userRepository.save(userInDataBase);
			log.info("IN updateUser - updated user by id:{}",user.getId());
			return userInDataBase; 
		}
		log.info("IN updateUser - updated user by id:{}",user.getId());
		throw new UserEntityNotFoundException("id",user.getId());
	}


	@Override
	public Long deleteUser(Long id) {
		User user = null;
		user = userRepository.getById(id);
		if(user==null||user.getId()==0) {
			log.warn("IN deleteUser - no user found by id:{}", id);
			throw new UserEntityNotFoundException("id",id);
		}
		userRepository.delete(user);
		return id;
	}
	
	private Session getOpenSessionForUser() {
		SessionFactory sessionFactory = new Configuration().configure()
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Profile.class)
				.addAnnotatedClass(Role.class)
				.buildSessionFactory();

		return sessionFactory.openSession();
	}

	
}