package com.payment.demo.model;

import com.payment.demo.encryption.JpaConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "MERCHANT_DETAILS")
public class MerchantDetails {

	    @Id
	    @Column(name = "MERCHANT_ID ")
	    private Long merchantId;

	    @Column(name = "NAME")
	    private String name;

	    @Convert(converter = JpaConverter.class)
	    @Column(name = "EMAIL")
	    private String email;

		@Column(name = "password")
	    private String password;
	    
	    @Convert(converter = JpaConverter.class)
	    @Column(name = "PHONE_NUMBER")
	    private String phoneNumber;

	    @ManyToOne
	    @JoinColumn(name = "CARD_DETAILS_ID")
	    private CardDetails cardDetails;

	    @Column(name = "PREFERRED_PAYMENT_TYPE")
	    private String preferredPaymentType;
	 
		public Long getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(Long merchantId) {
			this.merchantId = merchantId;
		}

		public String getName() {
			return name;
			
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		 public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}
		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public CardDetails getCardDetails() {
			return cardDetails;
		}

		public void setCardDetails(CardDetails cardDetails) {
			this.cardDetails = cardDetails;
		}

		public String getPreferredPaymentType() {
			return preferredPaymentType;
		}

		public void setPreferredPaymentType(String preferredPaymentType) {
			this.preferredPaymentType = preferredPaymentType;
		}

	    // getters and setters
	    
}

	  