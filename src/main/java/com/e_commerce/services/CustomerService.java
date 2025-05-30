package com.e_commerce.services;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.e_commerce.exceptions.CustomerException;
import com.e_commerce.exceptions.CustomerNotFoundException;
import com.e_commerce.models.Address;
import com.e_commerce.models.CreditCard;
import com.e_commerce.models.Customer;
import com.e_commerce.models.CustomerDTO;
import com.e_commerce.models.CustomerUpdateDTO;
import com.e_commerce.models.Order;
import com.e_commerce.models.SessionDTO;

public interface CustomerService {

//	--> Basic Customer Operations
	
	public Customer addCustomer(Customer customer) throws CustomerException;
	
	public Customer getCustomerById(Integer customerId) throws CustomerNotFoundException;
	
	public Customer getCurrentCustomerDetails(String token) throws LoginException;
	
	public List<Customer> getAllCustomers(String token) throws LoginException;
	
	public Customer getCustomerByToken(String token);
	
	
//	--> Customer updates
	
	public Customer updateCustomer(CustomerUpdateDTO customer, String token) throws CustomerNotFoundException,LoginException;
	
	public Customer updateContactDetails(CustomerUpdateDTO customer, String token) throws LoginException;
	
	public Customer updateCreditCard(CreditCard creditCard,String token) throws LoginException;
	
	public SessionDTO updatePassword(CustomerDTO customerDto, String token) throws CustomerNotFoundException;
	
	public Customer updateAddress(Address address, String type, String token) throws CustomerException;
	
	
//	--> Delete Customer
	
	public String deleteCustomer(CustomerDTO customerDto,String token) throws CustomerNotFoundException;
	
	
//	--> Customer Orders
	
	public List<Order> getCustomerOrders(String token) throws CustomerException;
	
}
