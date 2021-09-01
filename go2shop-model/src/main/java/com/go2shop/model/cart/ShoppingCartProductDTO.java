package com.go2shop.model.cart;

import javax.validation.constraints.NotNull;

/**
 * 
 * <Write a short description on the purpose of the class>
 * 
 * @author P1326154 Created Date Aug 10, 2021 2:41:13 PM
 * 
 */
public class ShoppingCartProductDTO {

	@NotNull
	private Long id;

	@NotNull
	private int shoppingCart;

	@NotNull
	private Long productID;

	@NotNull
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(int shoppingCart) {
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
