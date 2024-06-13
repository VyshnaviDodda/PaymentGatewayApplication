package com.payment.demo.DTO;

import java.sql.Timestamp;

import com.payment.demo.Enums.ClearanceStatusEnum;
import com.payment.demo.Enums.TransactionStatusEnum;

public class TransactionResponseDTO 
{
	    private Long orderId; 
	    private Double amount;
	    private String paymentType;
	    private TransactionStatusEnum transactionStatus;
	    private ClearanceStatusEnum clearanceStatus;
	    private String locationLatitude;
	    private String locationLongitude;
	    private Timestamp transactionTime;
	   private CardDetailsDTO cardDetailsDTO;
	   private MerchantDetailsDTO merchantDetailsDTO;
	    public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public String getPaymentType() {
			return paymentType;
		}
		public void setPaymentType(String paymentType) {
			this.paymentType = paymentType;
		}
		public TransactionStatusEnum getTransactionStatus() {
			return transactionStatus;
		}
		public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
			this.transactionStatus = transactionStatus;
		}
		public ClearanceStatusEnum getClearanceStatus() {
			return clearanceStatus;
		}
		public void setClearanceStatus(ClearanceStatusEnum clearanceStatus) {
			this.clearanceStatus = clearanceStatus;
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
		public Timestamp getTransactionTime() {
			return transactionTime;
		}
		public void setTransactionTime(Timestamp transactionTime) {
			this.transactionTime = transactionTime;
		}
		public CardDetailsDTO getCardDetailsDTO() {
			return cardDetailsDTO;
		}
		public void setCardDetailsDTO(CardDetailsDTO cardDetailsDTO) {
			this.cardDetailsDTO = cardDetailsDTO;
		}
		public MerchantDetailsDTO getMerchantDetailsDTO() {
			return merchantDetailsDTO;
		}
		public void setMerchantDetailsDTO(MerchantDetailsDTO merchantDetailsDTO) {
			this.merchantDetailsDTO = merchantDetailsDTO;
		}
		
	   
}
