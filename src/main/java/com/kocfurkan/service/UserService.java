package com.kocfurkan.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kocfurkan.dao.UserDaoImp;
import com.kocfurkan.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/userService")
@Api(value = "User Service API")
public class UserService {

	
	@Autowired
	UserDaoImp userDao;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/saveUser", consumes = "application/json")
	@ApiOperation(value = "This endpoint saves user", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User save is successfuly"),
			@ApiResponse(code = 401, message = "User save is unauthorized"),
			@ApiResponse(code = 403, message = "User save is forbidden"),
			@ApiResponse(code = 404, message = "saveUser endpoint isn't found"),
	})
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		return ResponseEntity.ok(userDao.saveUser(user));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/newUserActivition")
	@ApiOperation(value = "This endpoint does activition new user", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "New user activation is successfuly"),
			@ApiResponse(code = 401, message = "New user activation is unauthorized"),
			@ApiResponse(code = 403, message = "New user activation is forbidden"),
			@ApiResponse(code = 404, message = "newUserActivition endpoint isn't found"),
	})
	public String newUserActivition(@RequestParam String param, @RequestParam String email) {
		boolean isActive = userDao.isActivation(param, email);
		if (isActive) {
			return "User is activition success";
		} else {
			return "User is activition failed";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/removeUser")
	@ApiOperation(value = "This endpoint removes user by userId")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User remove is successfuly"),
			@ApiResponse(code = 401, message = "User remove is unauthorized"),
			@ApiResponse(code = 403, message = "User remove is forbidden"),
			@ApiResponse(code = 404, message = "removeUser endpoint isn't found"),
	})
	public String removeUser(@RequestParam long userId) {
		return userDao.removeUser(userId);
	}
}
