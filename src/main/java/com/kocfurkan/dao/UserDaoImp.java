package com.kocfurkan.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kocfurkan.controller.UserMailController;
import com.kocfurkan.entity.User;
import com.kocfurkan.repository.UserRepository;

@Service
public class UserDaoImp implements UserDao {

	private Logger logger = Logger.getLogger(UserDaoImp.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMailController userMail;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String saveUser(User user) {
		logger.info("method begin-----> saveUser(user) user.id" + user.getId());
		String activitionToken = UUID.randomUUID().toString();
		user.setActivationToken(activitionToken);
		user.setActive(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedDate(new Date());
		userRepository.save(user);
		logger.info("user is saved. userId: " + user.getId());
		userMail.newUserActivitionMail(user.getEmail());
		System.out.println("user Password" + user.getPassword());
		return "User Saving Success. User is disabled";
	}

	@Override
	public Object getUserWithEmail(String email) {
		logger.info("method begin-----> getUserWithEmail(email) email: " + email);
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
				findUser.setPassword(user.getPassword());
				findUser.setActivationToken(user.getActivationToken());
				i++;
			}
		}
		if (i == 0) {
			logger.info("user not found");
			return "Bulunamadi";
		} else {
			logger.info("user is found --> id :" + findUser.getId());
			return findUser;
		}
	}

	@Override
	public boolean isActivation(String token, String email) {
		logger.info("method begin-----> isActivation(token,email) email: " + email);
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
			logger.info("User is active userId :"+user.getId());
			return true;
		}
	}

	@Override
	public String removeUser(Long userId) {
		logger.info("method begin-----> removeUser(userId) userId: " + userId);
		boolean isUserExists = userRepository.existsById(userId);
		logger.info("isUserExists : " + isUserExists);
		long userCount = userRepository.count();
		if (isUserExists) {
			User user = userRepository.findById(userId).get();
			userRepository.delete(user);
			if (userCount == userRepository.count() - 1) {
				return "Kullanici basarili bir sekilde silindi";
			} else {
				isUserExists = userRepository.existsById(userId);
				if (isUserExists) {
					return "Kullanici silme islemi gerceklesti ancak bu idye sahip kullanici duruyor";
				} else {
					// Kullanıcı Silinmiş ancak count düşmemiş
					return "Bu idye sahip kullanici silindi ancak count dusmedi";
				}
			}
		} else {
			return userId + "Kullanicisi bulunamadi";
		}
	}

	@Override
	public boolean passwordMatches(String encodePassword, String password) {
		boolean isTrue = passwordEncoder.matches(password, encodePassword);
		return isTrue;
	}

}
