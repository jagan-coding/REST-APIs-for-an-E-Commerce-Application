package com.e_commerce.services;

import com.e_commerce.models.CustomerDTO;
import com.e_commerce.models.SellerDTO;
import com.e_commerce.models.UserSession;

public interface AuthService {

//	Customer Authentication Service
	
	public UserSession loginCustomer(CustomerDTO customer);
	
	public void logoutCustomer(String token);
	
//	Check the Token
	
	public void checkTokenStatus(String token);
	
//	Delete Expired Token
	
	public void deleteExpiredToken();
	
//	Seller Authentication Service
	
	public UserSession loginSeller(SellerDTO seller);
	
	public void logoutSeller(String token);
	
}
