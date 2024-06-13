
package com.payment.demo.controller;

import com.payment.demo.model.CardDetails;
import com.payment.demo.model.MerchantDetails;

public class CreateMerchantRequest 
{
	private MerchantDetails merchantDetails;
    private CardDetails cardDetails;
    
    //Getter Setter Methods
	public MerchantDetails getMerchantDetails() {
		return merchantDetails;
	}
	public void setMerchantDetails(MerchantDetails merchantDetails) 
	{
		this.merchantDetails = merchantDetails;
	}
	
	public CardDetails getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}
    
    
}