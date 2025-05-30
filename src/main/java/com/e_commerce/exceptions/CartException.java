package com.e_commerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CartException extends RuntimeException{

	public CartException(String message) {
		super(message);
		
	}

	
}
