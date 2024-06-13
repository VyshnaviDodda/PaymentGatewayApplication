package com.payment.demo.DTO;

import com.payment.demo.Enums.ClearanceStatusEnum;

public class OrderSummary {
	
	     private  MerchantDTO merchantDTO;
		    private ClearanceStatusEnum clearanceStatus;
	     
	     
	    public MerchantDTO getMerchantDetailsDTO() {
			return merchantDTO;
		}
		public void setMerchantDetailsDTO(MerchantDTO merchantDTO) {
			this.merchantDTO = merchantDTO;
		}
		
		public ClearanceStatusEnum getClearanceStatus() {
			return clearanceStatus;
		}
		public void setClearanceStatus(ClearanceStatusEnum clearanceStatus) {
			this.clearanceStatus = clearanceStatus;
		}
	
	
		
		


}