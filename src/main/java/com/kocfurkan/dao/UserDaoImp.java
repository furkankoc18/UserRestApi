package com.kocfurkan.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kocfurkan.controller.UserMailController;
import com.kocfurkan.entity.Role;
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
		String activitionToken = UUID.randomUUID().toString();
		user.setActivationToken(activitionToken);
		user.setStatus(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedDate(new Date());
		userRepository.save(user);
		userMail.newUserActivitionMail(user.getEmail());
		return "User Saved Success. User is disabled";
	}

	@Override
	public Object getUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null)
			return user;
		else
			return "bulunamadı";
	}

	@Override
	public boolean isActivation(String token, String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			if (user.getActivationToken().equals(token)) {
				user.setStatus(true);
				userRepository.save(user);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		/*
		 * List<User> allUser = (List<User>) userRepository.findAll(); int i = 0; for
		 * (User user : allUser) { if (user.getEmail().equals(email) &&
		 * user.getActivationToken().equals(token)) { i++; } } if (i == 0) { return
		 * false; } else { User user = (User) getUserByEmail(email);
		 * user.setStatus(true); userRepository.save(user); return true; }
		 */
	}

	@Override
	public String removeUserById(Long userId) {
		boolean isUserExists = userRepository.existsById(userId);
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
			return userId + " Kullanicisi bulunamadi";
		}
	}

	@Override
	public boolean passwordMatches(String encodePassword, String password) {
		boolean isTrue = passwordEncoder.matches(password, encodePassword);
		return isTrue;
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = new ArrayList<>();
		for (Iterator<User> i = userRepository.findAll().iterator(); i.hasNext();) {
			User user = (User) i.next();
			users.add(user);
		}

		return users;
	}

	@Override
	public String removeUserByEmail(String email) {
		Long isDeleted = userRepository.removeByEmail(email);
		if (isDeleted == 0) {
			return email + " email kullanici bulunamadı!!";
		} else {
			return email + " email kullanici silindi";
		}
	}

	@Override
	public List<User> getUserListByRole(Role role) {
		List<User> users = userRepository.getUserListByRole(role.getId());

		return users;
	}

	@Override
	public User getUserById(Long id) {

		try {
			User user = userRepository.findById(id).get();
			if (user != null) {
				return user;
			}
		} catch (Exception e) {

		}
		return null;
	}

}
