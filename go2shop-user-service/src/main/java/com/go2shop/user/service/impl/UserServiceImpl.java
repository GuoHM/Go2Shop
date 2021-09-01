package com.go2shop.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.go2shop.model.user.UserDTO;
import com.go2shop.user.entity.User;
import com.go2shop.user.repository.UserRepository;
import com.go2shop.user.service.UserService;
import com.go2shop.user.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO user) {
		User savedUser = userRepository.saveAndFlush(userMapper.toEntity(user));
		return userMapper.toDto(savedUser);
	}

}
