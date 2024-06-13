package com.payment.demo.DTO;

public class CardDetailsDTO 
{
    private String cardNumber;
    private String name;
	private String expiryDate;

    public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String string) {
		this.cardNumber = string;
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
	public CardDetailsDTO(String cardNumber, String name, String expiryDate) {
		super();
		this.cardNumber = cardNumber;
		this.name = name;
		this.expiryDate = expiryDate;
	}
	public CardDetailsDTO() {
		// TODO Auto-generated constructor stub
	}
}
