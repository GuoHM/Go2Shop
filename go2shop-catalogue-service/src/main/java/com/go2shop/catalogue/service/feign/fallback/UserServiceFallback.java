package com.go2shop.catalogue.service.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.go2shop.catalogue.service.feign.UserService;
import com.go2shop.model.user.UserDTO;

public class UserServiceFallback implements UserService {

	@Override
	public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
		return ResponseEntity.badRequest().body(null); 
	}

}
