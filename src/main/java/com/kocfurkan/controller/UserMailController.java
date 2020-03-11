package com.kocfurkan.controller;


import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import com.kocfurkan.dao.UserDaoImp;
import com.kocfurkan.entity.User;

@Controller
public class UserMailController {

	private final Logger logger = Logger.getLogger(UserMailController.class);

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Properties properties;
	@Autowired
	private UserDaoImp userDaoImp;

	public void newUserActivitionMail(String toEmail) {
		try {
			logger.info("method begin-----> newUserActivitionMail(toEmail) toEmail : " + toEmail);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo("furkankoc18@gmail.com");
			mailMessage.setSubject("User Active Message");
			Object user = userDaoImp.getUserByEmail(toEmail);// UUID.randomUUID().toString();
			String token = "";
			if (user instanceof User) {
				User user2 = (User) user;
				token = user2.getActivationToken();
			} else {
				// kullanıcı yok
			}
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
			logger.info("application.properties file setted");
			String baseUrl = "http://localhost:" + properties.getProperty("server.port");
			String path = "/userService/newUserActivition?param=";
			String email = "furkankoc18@gmail.com";
			mailMessage.setText(baseUrl + path + token + "&email=" + email);
			logger.info("mailmessage text : " + mailMessage.getText());
			logger.info("mail sending...");
			javaMailSender.send(mailMessage);
			logger.info("mail sended");
		} catch (Exception e) {
			System.out.println("sendEmail Err => " + e.toString());
			logger.error("newUserActivitionMail exception e: " + e.toString());
			return;
		}

	}

}
