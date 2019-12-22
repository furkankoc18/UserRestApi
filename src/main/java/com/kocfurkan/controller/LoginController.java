package com.kocfurkan.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.kocfurkan.dao.UserDao;
import com.kocfurkan.entity.User;
import com.kocfurkan.model.Login;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UserDao userDao;

	public String userLogin(Login login) {
		logger.info("method begin-----> userLogin()");
		Object object = userDao.getUserWithEmail(login.getEmail());
		if (object instanceof User) {
			User user = (User) object;
			if (userDao.passwordMatches(user.getPassword(), login.getPassword())) {
				if (user.isActive()) {
					logger.info("user logged in userEmail: " + login.getEmail());
					return "login success";
				} else {
					logger.info("login failed user not active  userEmail: " + login.getEmail());
					return "login failed user not active";
				}

			} else {
				logger.info("login failed password userEmail: " + login.getEmail());
				return "login faied password";
			}

		} else {
			logger.info("login failed userEmail: " + login.getEmail());
			return "login failed";
		}
	}

}
