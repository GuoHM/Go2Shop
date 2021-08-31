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
 * @author P1326154 
 * Created Date Aug 10, 2021 3:00:27 PM 
 * 
*/
@Entity
@Table(name = "TB_SHOPPING_CART_has_TB_PRODUCT")
public class ShoppingCartProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@NotNull
	@Column(name = "TB_SHOPPING_CART_ID")
	private int shoppingCart;
	
	@NotNull
	@Column(name = "TB_PRODUCT_ID")
	private int productID;
	
	@NotNull
	@Column(name = "Quantity")
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
