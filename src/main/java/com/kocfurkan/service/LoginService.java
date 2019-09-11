package com.kocfurkan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kocfurkan.controller.LoginController;
import com.kocfurkan.model.Login;

@RestController
@RequestMapping("/loginService")
public class LoginService {

	@Autowired
	private LoginController loginController;
	
	@PostMapping(value = "/userLogin",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
	public String userLogin(@RequestBody Login login) {
		return loginController.userLogin(login);
	}
	
}
