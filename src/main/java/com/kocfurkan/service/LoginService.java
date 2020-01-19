package com.kocfurkan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kocfurkan.controller.LoginController;
import com.kocfurkan.model.Login;
import com.kocfurkan.model.LoginResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/loginService")
@Api(value = "Login Service API")
public class LoginService {

	@Autowired
	private LoginController loginController;
	
	@PostMapping(value = "/userLogin",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "This endpoint does user login processes ", response = LoginResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User login is successfuly"),
			@ApiResponse(code = 401, message = "User login is unauthorized"),
			@ApiResponse(code = 403, message = "User login is forbidden"),
			@ApiResponse(code = 404, message = "userLogin endpoint isn't found"),
	})
	public LoginResponse userLogin(@RequestBody Login login) {
		return loginController.userLogin(login);
	}
	
}
