package com.go2shop.authentication.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.go2shop.authentication.model.Authority;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AuthorityUtil {

	private AuthorityUtil() {
	}

	public static List<GrantedAuthority> createGrantedAuthorities(List<Authority> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
				.collect(Collectors.toList());
	}

	public static List<GrantedAuthority> createGrantedAuthorities(String... authorities) {
		return Stream.of(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public static String[] getAuthorities(UserDetails user) {
		return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
	}
}
