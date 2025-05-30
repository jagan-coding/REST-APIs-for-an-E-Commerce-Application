package com.e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.models.Customer;
import com.e_commerce.models.CustomerDTO;
import com.e_commerce.models.Seller;
import com.e_commerce.models.SellerDTO;
import com.e_commerce.models.UserSession;
import com.e_commerce.services.AuthService;
import com.e_commerce.services.CustomerService;
import com.e_commerce.services.SellerService;

@RestController
public class AuthController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private AuthService authService;
	
//	Register a Customer
	@PostMapping(value="/api/auth/register/cus")
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
		return new ResponseEntity<>(customerService.addCustomer(customer),HttpStatus.CREATED);
	}
	
//	Login Customer
	@PostMapping(value="/api/auth/login/cus")
	public ResponseEntity<UserSession> loginCustomer(@RequestBody CustomerDTO customerDto){
		return new ResponseEntity<>(authService.loginCustomer(customerDto),HttpStatus.OK);
	}
	
//	Logout Customer
	@PostMapping(value="/api/auth/logout/cus")
	public ResponseEntity<String> logoutCustomer(@RequestHeader String token){
		authService.logoutCustomer(token);
		return new ResponseEntity<>("Logout Successfully.",HttpStatus.OK);
	}
	
//	Register a Seller
	@PostMapping(value="/api/auth/register/sel")
	public ResponseEntity<Seller> registerSeller(@RequestBody Seller seller){
		return new ResponseEntity<>(sellerService.addSeller(seller),HttpStatus.CREATED);
	}
	
//	Login Seller
	@PostMapping(value="/api/auth/login/sel")
	public ResponseEntity<UserSession> loginSeller(@RequestBody SellerDTO sellerDto){
		return new ResponseEntity<>(authService.loginSeller(sellerDto),HttpStatus.OK);
	}
	
//	Logout Seller
	@PostMapping(value="/api/auth/logout/sel")
	public ResponseEntity<String> logoutSeller(@RequestHeader String token){
		authService.logoutSeller(token);
		return new ResponseEntity<>("Logout Successfully.",HttpStatus.OK);
	}

}
