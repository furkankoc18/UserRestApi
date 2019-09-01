package com.kocfurkan.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kocfurkan.controller.UserMailController;
import com.kocfurkan.entity.User;
import com.kocfurkan.repository.UserRepository;

@Service
public class UserDaoImp implements UserDao {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMailController userMail;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String saveUser(User user) {
		String activitionToken=UUID.randomUUID().toString();
		user.setActivationToken(activitionToken);
		user.setActive(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		userMail.newUserActivitionMail(user.getEmail());
		System.out.println("user Password"+user.getPassword());
		return "User Saving Success. User is disabled";
	}

	@Override
	public Object getUserWithEmail(String email) {
		User findUser = new User();
		List<User> allUser = (List<User>) userRepository.findAll();
		int i = 0;
		for (User user : allUser) {
			if (user.getEmail().equals(email)) {
				findUser.setActive(user.isActive());
				findUser.setEmail(user.getEmail());
				findUser.setId(user.getId());
				findUser.setName(user.getName());
				findUser.setSurname(user.getSurname());
				findUser.setRole(user.getRole());
				findUser.setPassword(user.getEmail());
				findUser.setActivationToken(user.getActivationToken());
				i++;
			}
		}
		if (i == 0) {
			return "Bulunamadi";
		} else {
			return findUser;
		}
	}

	@Override
	public boolean isActivation(String token, String email) {
		List<User> allUser = (List<User>) userRepository.findAll();
		int i = 0;
		for (User user : allUser) {
			if (user.getEmail().equals(email) && user.getActivationToken().equals(token)) {
				i++;
			}
		}
		if (i == 0) {
			return false;
		} else {
			User user = (User) getUserWithEmail(email);
			user.setActive(true);
			userRepository.save(user);
			return true;
		}
	}

}
