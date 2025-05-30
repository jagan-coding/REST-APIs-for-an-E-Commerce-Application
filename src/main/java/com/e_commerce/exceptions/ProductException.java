package com.e_commerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductException extends RuntimeException{
	
	public ProductException(String message) {
		super(message);
	}
	
	
}
