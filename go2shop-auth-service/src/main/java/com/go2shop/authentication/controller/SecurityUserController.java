package com.go2shop.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.authentication.model.UserRegisterDTO;
import com.go2shop.authentication.service.UserAuthService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.user.UserDTO;

@RestController
@RequestMapping("/securityUser")
public class SecurityUserController extends BaseController {

	@Autowired
	private UserAuthService userAuthService;

	@PostMapping(value = "/register")
	public ResponseEntity<UserDTO> register(@RequestBody(required = true) UserRegisterDTO userRegister)
			throws BusinessException {
		return ResponseEntity.ok().body(userAuthService.registerUser(userRegister));
	}
}
