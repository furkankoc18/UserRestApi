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

@RestController
@RequestMapping("/userService")
public class UserService {

	
	@Autowired
	UserDaoImp userDao;

	@ResponseBody
	@RequestMapping(value = "/saveUser", consumes = "application/json")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		return ResponseEntity.ok(userDao.saveUser(user));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/newUserActivition")
	public String newUserActivition(@RequestParam String param, @RequestParam String email) {
		boolean isActive = userDao.isActivation(param, email);
		if (isActive) {
			return "User is activition success";
		} else {
			return "User is activition failed";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/removeUser")
	public String removeUser(@RequestParam long userId) {
		return userDao.removeUser(userId);
	}
}
