package com.kocfurkan.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kocfurkan.entity.Role;
import com.kocfurkan.entity.User;

@Component
public interface UserDao {

	String saveUser(User user);
	boolean isActivation(String token,String email);
	String removeUserById(Long userId);
	String removeUserByEmail(String email);
	List<User>getUserListByRole(Role role);
	User getUserById(Long id);
	Object getUserByEmail(String email);
	
	
	boolean passwordMatches(String encodePassword,String password);
	List<User>getAllUser();
}
