package com.go2shop.authentication.service.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.go2shop.authentication.service.feign.UserService;
import com.go2shop.model.user.UserDTO;

@Component
public class UserServiceFallback implements UserService {

	@Override
	public ResponseEntity<UserDTO> register(UserDTO user) {
		return ResponseEntity.badRequest().body(null); 
	}

}
