package com.go2shop.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.common.controller.BaseController;
import com.go2shop.model.user.UserDTO;
import com.go2shop.user.service.UserService;

@RestController
@RequestMapping(value = "/user")
@RefreshScope
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserDTO> getCatalogue(@RequestBody(required = true) UserDTO user) {
		return ResponseEntity.ok().body(userService.createUser(user));
	}

}
