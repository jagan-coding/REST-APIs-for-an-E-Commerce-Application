package com.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCard {
	
	private String cardNumber;
	private String cardValidity;
	private String cardCVV;

}
