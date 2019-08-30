package com.kocfurkan.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import com.kocfurkan.model.ActivationMessage;

@Controller
public class UserMailController {

	@Autowired
	private JavaMailSender javaMailSender;

	public void newUserActivitionMail(String toEmail) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo("furkankoc18@gmail.com");
			mailMessage.setSubject("User Active Message");

			String token = UUID.randomUUID().toString();
			String baseUrl = "http://localhost:8080";
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
