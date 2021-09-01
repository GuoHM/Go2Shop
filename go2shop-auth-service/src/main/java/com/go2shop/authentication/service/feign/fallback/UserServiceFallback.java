package com.go2shop.authentication.service.feign.fallback;

import org.springframework.http.ResponseEntity;

import com.go2shop.authentication.service.feign.UserService;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.user.UserDTO;

public class UserServiceFallback implements UserService {

	@Override
	public ResponseEntity<UserDTO> register(UserDTO user) {
		return ResponseEntity.badRequest().body(null); 
	}

}
