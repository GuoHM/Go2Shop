package com.go2shop.authentication.model;

import java.util.List;

public class User {
//		@Id
//		@Column(name = "ID", updatable = false)
//		@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

//		@Column(name = "USERNAME", unique = true, updatable = false)
	private String username;

//		@Column(name = "PASSWORD")
//		@NotNull
	private String password;
//
//		@Column(name = "EMAIL")
//		@NotNull
	private String email;
//
//		@Column(name = "ENABLED")
//		@NotNull
	private Boolean enabled;
//
//		@Column(name = "TITLE")
//		@NotNull
	private String title;
//
//		@Column(name = "PHONE")
//		@NotNull
//		private String phone;
//
//		@Column(name = "COMPANY")
//		@NotNull
	private String company;

//		@Column(name = "NAME")
//		@NotNull
	private String name;
//		
//		@Column(name = "SUBSCRIBE")
//		@NotNull
	private boolean subscribe;

//		@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
//		@JoinTable(name = "USER_AUTHORITY", joinColumns = {
//				@JoinColumn(name = "USER_ID", referencedColumnName = "ID", updatable = false) }, inverseJoinColumns = {
//						@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID", updatable = false) })
	private List<Authority> authorities;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

}
