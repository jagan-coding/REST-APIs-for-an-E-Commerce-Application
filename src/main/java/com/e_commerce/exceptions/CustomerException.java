package com.e_commerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerException extends RuntimeException{

	public CustomerException(String message) {
		super(message);
	}
	
}
