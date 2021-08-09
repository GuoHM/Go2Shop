package com.go2shop.authentication.service.impl;

import java.util.Arrays;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.go2shop.authentication.model.Authority;
import com.go2shop.authentication.model.AuthorityName;
import com.go2shop.authentication.model.User;
import com.go2shop.authentication.util.AuthorityUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = mockUser();
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		return create(user);
	}

	private static org.springframework.security.core.userdetails.User create(User user) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				AuthorityUtil.createGrantedAuthorities(user.getAuthorities()));
	}
	
	private User mockUser() {
		User result = new User();
		result.setUsername("Haoming");
		result.setPassword("$2a$12$wFNgZQoOMsQwlY.AScUel.tjpp2s4Y2mOL4s6zgJViXqOpv/s2vh2");
		Authority authority = new Authority();
		authority.setId(1L);
		authority.setName(AuthorityName.BUYER);
		result.setAuthorities(Arrays.asList(authority));
		return result;
	}
}
