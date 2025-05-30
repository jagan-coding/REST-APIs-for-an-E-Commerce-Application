package com.e_commerce.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.CustomerException;
import com.e_commerce.exceptions.CustomerNotFoundException;
import com.e_commerce.models.Address;
import com.e_commerce.models.Cart;
import com.e_commerce.models.CreditCard;
import com.e_commerce.models.Customer;
import com.e_commerce.models.CustomerDTO;
import com.e_commerce.models.CustomerUpdateDTO;
import com.e_commerce.models.Order;
import com.e_commerce.models.SessionDTO;
import com.e_commerce.models.UserSession;
import com.e_commerce.repositories.CustomerRepository;
import com.e_commerce.repositories.SessionRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Override
	public Customer getCustomerByToken(String token) {
		
		if(!token.startsWith("customer_"))
			throw new CustomerException("Invalid Token!");
		
		authService.checkTokenStatus(token);
		
		UserSession session = sessionRepository.findByToken(token)
				.orElseThrow(()->new CustomerException("Session Expired"));
		
		Customer customer = customerRepository.findById(session.getUserId())
				.orElseThrow(()->new CustomerNotFoundException("Customer Does not Found!"));
		
		return customer;
	}
	
	

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {
		
		boolean exists = customerRepository.findByMobileNo(customer.getMobileNo()).isPresent();
		
		if(exists) {
			throw new CustomerException("Aldready exists!");
		}
		
		customer.setCreatedOn(LocalDateTime.now());
		customer.setCustomerCart(new Cart());
		customer.setOrders(new ArrayList<>());
		
		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerById(Integer customerId) throws CustomerNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException("Customer Does Not found!"));
		return customer;
	}

	@Override
	public Customer getCurrentCustomerDetails(String token) throws LoginException{
		
		Customer customer = getCustomerByToken(token);
		
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers(String token) throws LoginException {
		
		if(!token.startsWith("seller_"))
			throw new LoginException("Invalid Token!");
		
		authService.checkTokenStatus(token);
		
		List<Customer> customers = customerRepository.findAll();
		
		return customers;
	}

	@Override
	public Customer updateCustomer(CustomerUpdateDTO customer, String token) throws CustomerNotFoundException,LoginException{
		
		Customer existingCustomer = getCustomerByToken(token);
		
		if(!existingCustomer.getPassword().equals(customer.getPassword()))
			throw new LoginException("Password Incorrect!");
		
		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());

		return customerRepository.save(existingCustomer);
	}

	@Override
	public Customer updateContactDetails(CustomerUpdateDTO customerDto, String token) throws LoginException {
		
		Customer customer = getCustomerByToken(token);
		
		if(!customer.getPassword().equals(customerDto.getPassword()))
			throw new LoginException("Password Incorrect!");
		
		customer.setMobileNo(customerDto.getMobileNo());
		customer.setEmailId(customerDto.getEmailId());
		
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCreditCard(CreditCard creditCard, String token) throws LoginException {
		
		Customer customer = getCustomerByToken(token);
		
		customer.setCreditCard(creditCard);
		
		return customerRepository.save(customer);
	}

	@Override
	public SessionDTO updatePassword(CustomerDTO customerDto, String token) throws CustomerNotFoundException {
		
		Customer customer = getCustomerByToken(token);
		
		if(customer.getMobileNo().equals(customerDto.getMobileNo()) 
				|| customer.getEmailId().equals(customerDto.getEmailId())) {
			customer.setPassword(customerDto.getPassword());
			customerRepository.save(customer);
			authService.logoutCustomer(token);
			
			return new SessionDTO(token,"Updated Password and Logged out! Please Login with new Password.");
		}
		else
			throw new CustomerException("Mobile No or Email Id does not Match our Records!");
	}

	@Override
	public Customer updateAddress(Address address, String type, String token) throws CustomerException {
		
		Customer customer = getCustomerByToken(token);
		
		customer.getAddress().put(type, address);
		
		return customerRepository.save(customer);
	}

	@Override
	public String deleteCustomer(CustomerDTO customerDto, String token) throws CustomerNotFoundException {
		
		Customer customer = getCustomerByToken(token);
		
		if(!customer.getPassword().equals(customerDto.getPassword())) 
			throw new CustomerException("Incorrect Password!");
		
		authService.logoutCustomer(token);
		customerRepository.delete(customer);
		
		return "Accound Deleted and Logged out Successfully!";
	
	}

	@Override
	public List<Order> getCustomerOrders(String token) throws CustomerException {
		
		Customer customer = getCustomerByToken(token);
		
		List<Order> orders = customer.getOrders();
		
		if(orders.isEmpty())
			throw new CustomerException("No orders Found!");
		
		return orders;
	}

}
