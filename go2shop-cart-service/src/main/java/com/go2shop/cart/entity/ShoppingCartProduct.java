package com.go2shop.cart.entity;

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
 * @author P1326154 Created Date Aug 10, 2021 3:00:27 PM
 * 
 */
@Entity
@Table(name = "TB_SHOPPING_CART_has_TB_PRODUCT")
public class ShoppingCartProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "TB_SHOPPING_CART_ID")
	private Long shoppingCart;

	@NotNull
	@Column(name = "TB_PRODUCT_ID")
	private Long productID;

	@NotNull
	@Column(name = "Quantity")
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(Long shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
