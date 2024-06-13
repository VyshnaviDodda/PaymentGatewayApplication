package com.payment.demo.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import com.payment.demo.Enums.ClearanceStatusEnum;
import com.payment.demo.Enums.TransactionStatusEnum;
import com.payment.demo.encryption.JpaConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

@Entity
@Table(name = "TRANSACTION_DETAILS")
public class TransactionDetails {

    @Id
    @Column(name = "ORDER_ID") 
    private Long orderId; 

//    @Convert(converter = JpaConverter.class)
    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "AMOUNT")
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerDetails customerDetails;

    @ManyToOne
    @JoinColumn(name = "MERCHANT_ID")
    private MerchantDetails merchantDetails;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "CARD_DETAILS_ID")
    private CardDetails cardDetails;

    @Column(name = "TRANSACTION_STATUS")
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum transactionStatus;

    @Column(name = "CLEARANCE_STATUS")
    @Enumerated(EnumType.STRING)
    private ClearanceStatusEnum clearanceStatus;

    @ManyToOne
    @JoinColumn(name = "CHANNEL_ID")
    private Channel channel;

    @Column(name = "LOCATION_LATITUDE")
    private String locationLatitude;

    @Column(name = "LOCATION_LONGITUDE")
    private String locationLongitude;
    
    @Column(name = "TRANSACTION_TIME")
    private Timestamp transactionTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId1(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomer( CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public MerchantDetails getMerchantDetails() {
		return merchantDetails;
	}

	public void setMerchant(MerchantDetails merchantDetails) {
		this.merchantDetails = merchantDetails;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
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

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
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

	public void setTransactionId(String transactionId2) {
		// TODO Auto-generated method stub
		
	}

	public void setTransactionTime(LocalDateTime now) {
		// TODO Auto-generated method stub
		
	}

	public void generateTransactionId() {
        // Generate unique transaction ID using timestamp and random value
        long currentTimeMillis = System.currentTimeMillis();
        int randomValue = new Random().nextInt(1000); // You can adjust the range

        this.transactionId = currentTimeMillis + "-" + randomValue;
    }
	
	public void setTransactionTime() {
        // Set the transactionTime to the current timestamp
        this.transactionTime = Timestamp.valueOf(LocalDateTime.now());
    }
	

}
