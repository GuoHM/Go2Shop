package com.go2shop.authentication.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jboss.aerogear.security.otp.api.Base32;

import com.go2shop.common.model.ActiveStatus;

@Entity
@Table(name = "TB_SECURITY_USER")
public class SecurityUser {
	
	public SecurityUser() {
		this.secret = Base32.random();
	}
	
	@Id
	@Column(name = "ID", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USERNAME", unique = true, updatable = false)
	private String username;

	@Column(name = "PASSWORD", length = 100)
	@NotNull
	private String password;

	@Column(name = "USER_ID")
	@NotNull
	private Long userId;

	@Column(name = "ENABLED", length = 1)
	@NotNull
	private ActiveStatus enabled;

	@Column(name = "IS_2FA_ENABLED")
	private boolean authEnabled;
	
	@Column(name = "SECRET")
	private String secret;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
	@JoinTable(name = "TB_USER_AUTHORITY", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID", updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID", updatable = false) })
	private List<Authority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ActiveStatus getEnabled() {
		return enabled;
	}

	public void setEnabled(ActiveStatus enabled) {
		this.enabled = enabled;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public boolean getAuthEnabled() {
		return this.authEnabled;
	}

	public void setAuthEnabled(boolean authEnabled) {
		this.authEnabled = authEnabled;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
