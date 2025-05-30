package com.e_commerce.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.CustomerNotFoundException;
import com.e_commerce.exceptions.SellerNotFoundException;
import com.e_commerce.exceptions.LoginException;
import com.e_commerce.models.Customer;
import com.e_commerce.models.CustomerDTO;
import com.e_commerce.models.Seller;
import com.e_commerce.models.SellerDTO;
import com.e_commerce.models.UserSession;
import com.e_commerce.repositories.CustomerRepository;
import com.e_commerce.repositories.SellerRepository;
import com.e_commerce.repositories.SessionRepository;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	 
	@Autowired
	private SessionRepository sessionRepository;
	
	@Override
	public UserSession loginCustomer(CustomerDTO customerDto) {
		Customer customer = customerRepository.findByMobileNo(customerDto.getMobileNo())
				.orElseThrow(()-> new CustomerNotFoundException("Customer not found with this mobile No!"));
		
		Optional<UserSession> existingCustomer = sessionRepository.findByUserId(customer.getCustomerId());
		
		if(existingCustomer.isPresent()) {
			UserSession session = existingCustomer.get();
			if(session.getSessionEndTime().isBefore(LocalDateTime.now()))
				throw new LoginException("User aldready Logged In!");
		}
		
		if(!customer.getPassword().equals(customerDto.getPassword())) {
			throw new LoginException("Incorrect Password! Try again!");
		}
		
//		Create New Session
		String token = "customer_"+UUID.randomUUID().toString().split("-")[0];
		
		UserSession newSession = UserSession.builder()
				.userId(customer.getCustomerId())
				.userType("customer")
				.sessionStartTime(LocalDateTime.now())
				.sessionEndTime(LocalDateTime.now().plusHours(1))
				.token(token)
				.build();
		
		return sessionRepository.save(newSession);
	}

	@Override
	public void logoutCustomer(String token) {
		UserSession session = sessionRepository.findByToken(token)
				.orElseThrow(()-> new LoginException("Invalid or aldready Logged out session!"));
		sessionRepository.delete(session);
	}

	@Override
	public void checkTokenStatus(String token) {
		UserSession session = sessionRepository.findByToken(token)
				.orElseThrow(()->new LoginException("Invalid Session Token! Please Log in"));
		
		if(session.getSessionEndTime().isBefore(LocalDateTime.now())) {
			sessionRepository.delete(session);
			deleteExpiredToken();
			throw new LoginException("Session Expired! Please log in again!");
		}	
	}

	@Override
	public void deleteExpiredToken() {
		List<UserSession> sessions = sessionRepository.findAll();
		for(UserSession session:sessions) {
			if(session.getSessionEndTime().isBefore(LocalDateTime.now()))
				sessionRepository.delete(session);
		}
		
	}

	@Override
	public UserSession loginSeller(SellerDTO sellerDto) {
		
		Seller seller = sellerRepository.findByMobileNo(sellerDto.getMobileNo())
				.orElseThrow(()-> new SellerNotFoundException("Seller does not found with this mobile No"));
		if(!seller.getPassword().equals(sellerDto.getPassword()))
			throw new LoginException("Password is Incorrect! Try again.");
		
//		Create new Session
		String token = "seller_"+UUID.randomUUID().toString().split("-")[0];
		
		UserSession newSession = UserSession.builder()
				.userId(seller.getSellerId())
				.userType("seller")
				.sessionStartTime(LocalDateTime.now())
				.sessionEndTime(LocalDateTime.now().plusHours(1))
				.token(token)
				.build();
		
		return sessionRepository.save(newSession);
	}

	@Override
	public void logoutSeller(String token) {
		UserSession session = sessionRepository.findByToken(token)
				.orElseThrow(()->new LoginException("Invalid Token!"));
		sessionRepository.delete(session);
	}

}
