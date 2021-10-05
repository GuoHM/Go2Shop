package com.go2shop.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.user.UserDTO;
import com.go2shop.user.entity.User;
import com.go2shop.user.repository.UserRepository;
import com.go2shop.user.service.UserService;
import com.go2shop.user.service.feign.ShoppingCartService;
import com.go2shop.user.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Override
	public UserDTO createUser(UserDTO user) throws BusinessException {
		User savedUser = userRepository.saveAndFlush(userMapper.toEntity(user));
		ShoppingCartDTO cart = new ShoppingCartDTO();
		cart.setUserId(savedUser.getId());
		ResponseEntity<ShoppingCartDTO> response = shoppingCartService.createCartForUser(cart);
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		}
		return userMapper.toDto(savedUser);
	}

	@Override
	public UserDTO getUserById(Long userId) throws BusinessException {
		Optional<User> savedUser = userRepository.findById(userId);
		if (!savedUser.isPresent()) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST); 
		}
		return userMapper.toDto(savedUser.get());
	}

}
