package com.payment.demo.DTO;

public class MerchantDetailsDTO 
{
	    private String name;
	    private String email;
	    private String phoneNumber;
	    
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
		public MerchantDetailsDTO(String name, String email, String phoneNumber, String preferredPaymentType) {
			super();
			this.name = name;
			this.email = email;
			this.phoneNumber = phoneNumber;
			
		}
		public MerchantDetailsDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
}
