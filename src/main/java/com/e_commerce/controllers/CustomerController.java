package com.e_commerce.controllers;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.e_commerce.exceptions.CustomerException;
import com.e_commerce.exceptions.CustomerNotFoundException;
import com.e_commerce.models.Address;
import com.e_commerce.models.CreditCard;
import com.e_commerce.models.Customer;
import com.e_commerce.models.CustomerDTO;
import com.e_commerce.models.CustomerUpdateDTO;
import com.e_commerce.models.Order;
import com.e_commerce.models.SessionDTO;
import com.e_commerce.services.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping(value="/api/cus/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int customerId){
		return new ResponseEntity<>(customerService.getCustomerById(customerId),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/cus/current")
	public ResponseEntity<Customer> getCurrentCustomer(@RequestHeader String token) throws LoginException{
		return new ResponseEntity<>(customerService.getCurrentCustomerDetails(token),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/cus/all")
	public ResponseEntity<List<Customer>> getAllCustomer(@RequestHeader String token) throws LoginException{
		return new ResponseEntity<>(customerService.getAllCustomers(token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/cus")
	public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerUpdateDTO customer ,@RequestHeader String token) throws CustomerNotFoundException, LoginException{
		return new ResponseEntity<>(customerService.updateCustomer(customer, token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/cus/upd/contact")
	public ResponseEntity<Customer> updateContactDetails(@RequestBody CustomerUpdateDTO customer ,@RequestHeader String token) throws LoginException{
		return new ResponseEntity<>(customerService.updateContactDetails(customer, token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/cus/upd/credit")
	public ResponseEntity<Customer> updateCreditCard(@RequestBody CreditCard creditCard ,@RequestHeader String token) throws LoginException{
		return new ResponseEntity<>(customerService.updateCreditCard(creditCard, token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/cus/upd/password")
	public ResponseEntity<SessionDTO> updatePassword(@RequestBody CustomerDTO customerDto ,@RequestHeader String token) throws LoginException{
		return new ResponseEntity<>(customerService.updatePassword(customerDto, token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/cus/upd/address")
	public ResponseEntity<Customer> updateAddress(@RequestBody Address address, @RequestParam String type, @RequestHeader String token) throws LoginException{
		return new ResponseEntity<>(customerService.updateAddress(address, type, token),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/api/cus")
	public ResponseEntity<String> deleteCustomer(@RequestBody CustomerDTO customerDto,@RequestHeader String token){
		customerService.deleteCustomer(customerDto, token);
		return new ResponseEntity<>("Customer Delected Successfully.",HttpStatus.OK);
	}
	
	@GetMapping(value="/api/cus/orders")
	public ResponseEntity<List<Order>> getCustomerOrders(@RequestHeader String token){
		return new ResponseEntity<>(customerService.getCustomerOrders(token),HttpStatus.OK);
	}
	
}
