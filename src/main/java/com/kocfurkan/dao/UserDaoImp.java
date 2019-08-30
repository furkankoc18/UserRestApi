package com.kocfurkan.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kocfurkan.controller.UserMailController;
import com.kocfurkan.entity.User;
import com.kocfurkan.repository.UserRepository;

@Service
public class UserDaoImp implements UserDao {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserMailController userMail;

	@Override
	public String saveUser(User user) {
		user.setActive(false);
		userRepository.save(user);
		userMail.newUserActivitionMail(user.getEmail());
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
