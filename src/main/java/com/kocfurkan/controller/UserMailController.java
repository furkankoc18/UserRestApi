package com.kocfurkan.controller;


import java.util.Properties;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import com.kocfurkan.dao.UserDaoImp;
import com.kocfurkan.entity.User;
import com.kocfurkan.util.FileUtil;

@Controller
public class UserMailController {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Properties properties;
	@Autowired
	private UserDaoImp userDaoImp;

	public void newUserActivitionMail(String toEmail) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo("furkankoc18@gmail.com");
			mailMessage.setSubject("User Active Message");
			Object user = userDaoImp.getUserWithEmail(toEmail);// UUID.randomUUID().toString();
			String token = "";
			if (user instanceof User) {
				User user2 = (User) user;
				token=user2.getActivationToken();
			}else {
				// kullanıcı yok
			}
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
			String baseUrl = "http://localhost:" + properties
					.getProperty("server.port");
			String path = "/userService/newUserActivition?param=";
			String email = "furkankoc18@gmail.com";
			mailMessage.setText(baseUrl + path + token + "&email=" + email);
			javaMailSender.send(mailMessage);
		} catch (Exception e) {
			System.out.println("sendEmail Err => " + e.toString());
			return;
		}

	}

}
