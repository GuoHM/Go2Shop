package com.go2shop.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 
 * 
 * <Write a short description on the purpose of the class> 
 * 
 * @author P1326154 
 * Created Date Aug 10, 2021 10:09:39 AM 
 * 
*/
@Entity
@Table(name = "TB_USER")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@NotBlank
	@Column(name = "CARD_NUMBER")
	private String cardNumber;
	
	@NotBlank
	@Column(name = "NAME")
	private String name;
	
	@NotBlank
	@Column(name = "EXPIRY")
	private String expiry;
	
	@NotBlank
	@Column(name = "ADDRESS")
	private String address;
	
	@NotBlank
	@Column(name = "CONTACT_DETAIL")
	private String contactDetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(String contactDetail) {
		this.contactDetail = contactDetail;
	}
	
}
