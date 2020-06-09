package com.kocfurkan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kocfurkan.dao.UserDao;
import com.kocfurkan.entity.Role;
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
	UserDao userDao;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/saveUser", consumes = "application/json")
	@ApiOperation(value = "This endpoint saves user", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User save is successfuly"),
			@ApiResponse(code = 401, message = "User save is unauthorized"),
			@ApiResponse(code = 403, message = "User save is forbidden"),
			@ApiResponse(code = 404, message = "saveUser endpoint isn't found"), })
	public ResponseEntity<String> saveUser(@RequestBody(required = true) User user) {
		return ResponseEntity.ok(userDao.saveUser(user));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/newUserActivition")
	@ApiOperation(value = "This endpoint does activition new user", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "New user activation is successfuly"),
			@ApiResponse(code = 401, message = "New user activation is unauthorized"),
			@ApiResponse(code = 403, message = "New user activation is forbidden"),
			@ApiResponse(code = 404, message = "newUserActivition endpoint isn't found"), })
	public ResponseEntity<String> newUserActivition(@RequestParam String param, @RequestParam String email) {
		boolean isActive = userDao.isActivation(param, email);
		if (isActive) {
			return ResponseEntity.ok("User is activition success");
		} else {
			return ResponseEntity.ok("User is activition failed");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/removeUser")
	@ApiOperation(value = "This endpoint removes user by userId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User remove is successfuly"),
			@ApiResponse(code = 401, message = "User remove is unauthorized"),
			@ApiResponse(code = 403, message = "User remove is forbidden"),
			@ApiResponse(code = 404, message = "removeUser endpoint isn't found"), })
	public ResponseEntity<String> removeUser(@RequestParam long userId) {
		return ResponseEntity.ok(userDao.removeUserById(userId));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllUser")
	@ApiOperation(value = "This endpoint returns all user list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users return is successfuly"),
			@ApiResponse(code = 401, message = "Users return is unauthorized"),
			@ApiResponse(code = 403, message = "Users return is forbidden"),
			@ApiResponse(code = 404, message = "getAllUser endpoint isn't found"), })
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.ok(userDao.getAllUsers());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUserByEmail")
	@ApiOperation(value = "This endpoint returns user by email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User return is successfuly"),
			@ApiResponse(code = 401, message = "User return is unauthorized"),
			@ApiResponse(code = 403, message = "User return is forbidden"),
			@ApiResponse(code = 404, message = "getUserByEmail endpoint isn't found"), })
	public ResponseEntity<Object> getUserByEmail(@RequestParam String email) {
		return ResponseEntity.ok(userDao.getUserByEmail(email));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/removeUserByEmail")
	@ApiOperation(value = "This endpoint removes user by email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User remove is successfuly"),
			@ApiResponse(code = 401, message = "User remove is unauthorized"),
			@ApiResponse(code = 403, message = "User remove is forbidden"),
			@ApiResponse(code = 404, message = "removeUserByEmail endpoint isn't found"), })
	public ResponseEntity<String> removeUserByEmail(@RequestParam String email) {
		return ResponseEntity.ok(userDao.removeUserByEmail(email));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUserListByRole")
	@ApiOperation(value = "This endpoint returns users by role")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users return is successfuly"),
			@ApiResponse(code = 401, message = "Users return is unauthorized"),
			@ApiResponse(code = 403, message = "Users return is forbidden"),
			@ApiResponse(code = 404, message = "getUserListByRole endpoint isn't found"), })
	public ResponseEntity<List<User>> getUserListByRole(Role role) {
		return ResponseEntity.ok(userDao.getUserListByRole(role));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUserById")
	@ApiOperation(value = "This endpoint returns user by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User return is successfuly"),
			@ApiResponse(code = 401, message = "User return is unauthorized"),
			@ApiResponse(code = 403, message = "User return is forbidden"),
			@ApiResponse(code = 404, message = "getUserListByRole endpoint isn't found"), })
	public ResponseEntity<User> getUserById(Long id) {
		return ResponseEntity.ok(userDao.getUserById(id));
	}
}
