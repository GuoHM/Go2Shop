package com.go2shop.user.service;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.user.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO user) throws BusinessException;
	
	UserDTO getUserById(Long userId) throws BusinessException;

}
