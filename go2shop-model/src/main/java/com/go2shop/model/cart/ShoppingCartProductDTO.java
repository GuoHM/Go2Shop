package com.go2shop.model.cart;

import javax.validation.constraints.NotNull;

/** 
 * 
 * <Write a short description on the purpose of the class> 
 * 
 * @author P1326154 
 * Created Date Aug 10, 2021 2:41:13 PM 
 * 
*/
public class ShoppingCartProductDTO {

	@NotNull
	private int id;
	
	@NotNull
	private int shoppingCart;
	
	@NotNull
	private int productID;
	
	@NotNull
	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(int shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
