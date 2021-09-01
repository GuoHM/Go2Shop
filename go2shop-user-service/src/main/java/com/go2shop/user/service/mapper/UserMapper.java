package com.go2shop.user.service.mapper;

import org.mapstruct.Mapper;

import com.go2shop.model.user.UserDTO;
import com.go2shop.user.entity.User;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

	public UserDTO toDto(User user);
	
	public User toEntity(UserDTO user);
}
