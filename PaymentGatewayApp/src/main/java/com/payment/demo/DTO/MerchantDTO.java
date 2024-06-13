package com.payment.demo.DTO;

public class MerchantDTO 
{
	        private Long MerchantId;
		    private String name;
		    private String email;
		    private String phoneNumber;
		    private String preferredPaymentType;
		    private CardDetailsDTO cardDetails;
		   
		    public Long getMerchantId() {
				return MerchantId;
			}
			public void setMerchantId(Long merchantId) {
				MerchantId = merchantId;
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
			public String getPhoneNumber() {
				return phoneNumber;
			}
			public void setPhoneNumber(String string) {
				this.phoneNumber = string;
			}
			public String getPreferredPaymentType() {
				return preferredPaymentType;
			}
			public void setPreferredPaymentType(String preferredPaymentType) {
				this.preferredPaymentType = preferredPaymentType;
			}
			public CardDetailsDTO getCardDetails() {
				return cardDetails;
			}
			public void setCardDetails(CardDetailsDTO cardDetails) {
				this.cardDetails = cardDetails;
			}
			
		    
		    
		

	}
