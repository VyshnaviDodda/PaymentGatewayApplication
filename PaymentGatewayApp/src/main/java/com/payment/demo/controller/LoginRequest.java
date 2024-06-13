package com.payment.demo.controller;

import jakarta.persistence.Column;

public class LoginRequest 
{
	private Long merchantId;
	private String password;
	
    public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginRequest(Long merchantId, String password) {
		super();
		this.merchantId = merchantId;
		this.password = password;
	}
	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
