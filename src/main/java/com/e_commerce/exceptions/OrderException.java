package com.e_commerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderException extends RuntimeException{
	
	
	public OrderException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
	
}
