package com.payment.demo.controller;

import com.payment.demo.model.CardDetails;

public class PaymentRequest 
  {
	private Long orderId;
     private double amount;
     private CardDetails cardDetails;
     private String PaymentType;
  // private String orderExpiryTime;
     private String locationLatitude;
     private String locationLongitude;

     
     public PaymentRequest(Long orderId, double amount, CardDetails cardDetails, String paymentType,
 			String locationLatitude, String locationLongitude) {
 		super();
 		this.orderId = orderId;
 		this.amount = amount;
 		this.cardDetails = cardDetails;
 		PaymentType = paymentType;
 		this.locationLatitude = locationLatitude;
 		this.locationLongitude = locationLongitude;
 	}
	  public PaymentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getOrderId() 
	  {
		  return orderId; 
	  } 
	  public void setOrderId(Long orderId) 
	  {
		  this.orderId = orderId;
	  }

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public CardDetails getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}
	 public String getPaymentType() {
			return PaymentType;
		}
		public void setPaymentType(String paymentType) {
			PaymentType = paymentType;
		}
		 public String getLocationLatitude() {
				return locationLatitude;
			}

			public void setLocationLatitude(String locationLatitude) {
				this.locationLatitude = locationLatitude;
			}

			public String getLocationLongitude() {
				return locationLongitude;
			}

			public void setLocationLongitude(String locationLongitude) {
				this.locationLongitude = locationLongitude;
			}
			@Override
			public String toString() {
				return "PaymentRequest [orderId=" + orderId + ", amount=" + amount + ", cardDetails=" + cardDetails
						+ ", PaymentType=" + PaymentType + ", locationLatitude=" + locationLatitude
						+ ", locationLongitude=" + locationLongitude + "]";
			}



	
	
	
	
	/*
	 * public String getOrderExpiryTime() { return orderExpiryTime; } public void
	 * setOrderExpiryTime(String orderExpiryTime) { this.orderExpiryTime =
	 * orderExpiryTime; }
	 */

	 
	
	}
