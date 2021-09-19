package com.go2shop.cart.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * <Write a short description on the purpose of the class>
 * 
 * @author P1326154 Created Date Aug 10, 2021 3:00:18 PM
 * 
 */
@Entity
@Table(name = "TB_SHOPPING_CART")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PRICE")
	private BigDecimal price;

	@Column(name = "DISCOUNT")
	private BigDecimal discount;

	@NotNull
	@Column(name = "TB_USER_ID")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", price=" + price + ", discount=" + discount + ", userId=" + userId + "]";
	}

}
