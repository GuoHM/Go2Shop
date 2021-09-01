package com.go2shop.model.order;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * <Write a short description on the purpose of the class>
 * 
 * @author P1326154 Created Date Aug 10, 2021 3:06:24 PM
 * 
 */
public class OrderDTO {

	@NotNull
	private Long id;

	@NotBlank
	@Size(max = 25)
	private String status;

	@NotNull
	private LocalDateTime orderDate;

	@NotNull
	private Long userID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

}
