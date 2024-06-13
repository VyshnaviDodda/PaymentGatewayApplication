package com.payment.demo.model;

//import com.payment.demo.encryption.CardNumJpaConverter;
import com.payment.demo.encryption.JpaConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CARD_DETAILS")
public class CardDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

//	    @Convert(converter = CardNumJpaConverter.class)
	@Column(name = "CARD_NUMBER")
	private String cardNumber;

	@Column(name = "NAME")
	private String name;

	@Convert(converter = JpaConverter.class)
	@Column(name = "EXPIRY_DATE")
	private String expiryDate;

	@Convert(converter = JpaConverter.class)
	@Column(name = "CVV")
	private String cvv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}