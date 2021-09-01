package com.go2shop.model.product;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * <Write a short description on the purpose of the class>
 * 
 * @author P1326154 Created Date Aug 10, 2021 3:19:55 PM
 * 
 */
public class ProductDTO {

	@NotNull
	private Long id;

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 255)
	private String description;

	@NotNull
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;

	@NotNull
	private int stock;

	@NotNull
	private Long userID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

}
