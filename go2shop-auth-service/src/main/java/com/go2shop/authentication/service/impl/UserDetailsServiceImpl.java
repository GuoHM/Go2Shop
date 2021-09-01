package com.go2shop.authentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.go2shop.authentication.model.entity.SecurityUser;
import com.go2shop.authentication.repository.SecurityUserRepository;
import com.go2shop.authentication.util.AuthorityUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	SecurityUserRepository userAuthRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		SecurityUser user = userAuthRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		return create(user);
	}

	private static org.springframework.security.core.userdetails.User create(SecurityUser user) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				AuthorityUtil.createGrantedAuthorities(user.getAuthorities()));
	}
	
}
