package com.go2shop.authentication.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.go2shop.authentication.service.feign.fallback.UserServiceFallback;
import com.go2shop.model.user.UserDTO;

@FeignClient(value = "user-service", fallback = UserServiceFallback.class)
public interface UserService {
	
	@PostMapping("/user/create")
	ResponseEntity<UserDTO> register(@RequestBody(required = true) UserDTO user);
}
