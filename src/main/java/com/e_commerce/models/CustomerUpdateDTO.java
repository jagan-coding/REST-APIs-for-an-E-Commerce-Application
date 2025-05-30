package com.e_commerce.models;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {

	private String firstName;
	private String lastName;
	
	@Column(unique=true)
	private String mobileNo;
	
	@Column(unique = true)
	private String emailId;
	
	private String password;
	private Map<String, Address> address = new HashMap<>();
}
