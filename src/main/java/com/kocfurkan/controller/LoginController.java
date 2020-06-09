package com.kocfurkan.controller;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kocfurkan.dao.UserDao;
import com.kocfurkan.entity.User;
import com.kocfurkan.model.Login;
import com.kocfurkan.model.LoginResponse;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UserDao userDao;

	public LoginResponse userLogin(Login login) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		if (!pattern.matcher(login.getEmail()).matches()) {
			return new LoginResponse(false, "Email is not validate!!");
		} else {
			Object object = userDao.getUserByEmail(login.getEmail());
			if (object instanceof User) {
				User user = (User) object;
				if (userDao.passwordMatches(user.getPassword(), login.getPassword())) {
					if (user.isStatus()) {
						logger.info("user logged in userEmail: " + login.getEmail());
						Algorithm algorithm = Algorithm.HMAC256("secret");
						String token = JWT.create().withIssuer(login.getEmail()).withSubject("userLogin")
								.withClaim("email", login.getEmail()).withClaim("password", login.getPassword())
								.withIssuedAt(new Date()).sign(algorithm);
						return new LoginResponse(true, token);
					} else {
						return new LoginResponse(false, "login failed user not active");
					}

				} else {
					return new LoginResponse(false, "login faied password");
				}

			} else {
				return new LoginResponse(false, "login failed");
			}

		}
	}

}
