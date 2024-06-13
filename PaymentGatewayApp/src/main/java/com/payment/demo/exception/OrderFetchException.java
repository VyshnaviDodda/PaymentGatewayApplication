package com.payment.demo.exception;

public class OrderFetchException extends Exception 
{
	public OrderFetchException(String message) {
        super(message);
    }

    public OrderFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
