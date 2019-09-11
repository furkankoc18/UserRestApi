package com.kocfurkan.dao;

import org.springframework.stereotype.Component;

import com.kocfurkan.entity.User;

@Component
public interface UserDao {

	String saveUser(User user);
	Object getUserWithEmail(String email);
	boolean isActivation(String token,String email);
	String removeUser(Long userId);
	boolean passwordMatches(String encodePassword,String password);
}
