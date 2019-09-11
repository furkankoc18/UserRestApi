package com.kocfurkan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.kocfurkan.dao.UserDao;
import com.kocfurkan.entity.User;
import com.kocfurkan.model.Login;

@Controller
public class LoginController {

	@Autowired
	private UserDao userDao;
	


	public String userLogin(Login login) {

		Object object = userDao.getUserWithEmail(login.getEmail());

		if (object instanceof User) {
			User user=(User)object;
			if(userDao.passwordMatches(user.getPassword(), login.getPassword())) {
				if(user.isActive()) {
					return "login success";	
				}else {
					return "login failed user not active";
				}
				
			}else {
				return "login faied password";
			}
			
		} else {
			return "login failed";
		}
	}

}
