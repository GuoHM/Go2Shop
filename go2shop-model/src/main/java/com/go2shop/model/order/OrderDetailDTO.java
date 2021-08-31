package com.go2shop.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 
 * 
 * <Write a short description on the purpose of the class> 
 * 
 * @author P1326154 
 * Created Date Aug 10, 2021 3:06:33 PM 
 * 
*/
public class OrderDetailDTO {

	@NotNull
	private int id;
	
	@NotBlank
	private LocalDateTime orderDate;
	
	private LocalDateTime orderReceived;
	
	private LocalDateTime paymentDate;
	
	private BigDecimal payment;
	
	@NotNull
	private int orderID;
	
	@NotNull
	private int productID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getOrderReceived() {
		return orderReceived;
	}

	public void setOrderReceived(LocalDateTime orderReceived) {
		this.orderReceived = orderReceived;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}
	
}
