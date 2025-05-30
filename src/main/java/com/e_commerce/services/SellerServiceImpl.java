package com.e_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.SellerException;
import com.e_commerce.exceptions.SellerNotFoundException;
import com.e_commerce.models.Seller;
import com.e_commerce.models.SellerDTO;
import com.e_commerce.models.SessionDTO;
import com.e_commerce.models.UserSession;
import com.e_commerce.repositories.SellerRepository;
import com.e_commerce.repositories.SessionRepository;

@Service
public class SellerServiceImpl implements SellerService{
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Override
	public Seller getSellerByToken(String token) {
		
		if(!token.startsWith("seller_"))
			throw new SellerException("Invalid Token!");
		
		authService.checkTokenStatus(token);
		
		UserSession session = sessionRepository.findByToken(token)
				.orElseThrow(()-> new SellerException("Session Expired!"));
		
		Seller seller = sellerRepository.findById(session.getUserId())
				.orElseThrow(()->new SellerNotFoundException("Seller Does not Found!"));
		
		return seller;
	}

	@Override
	public Seller addSeller(Seller seller) {
		return sellerRepository.save(seller);
	}

	@Override
	public List<Seller> getAllSellers() throws SellerException {
		
		List<Seller> sellers = sellerRepository.findAll();
		
		if(sellers.isEmpty())
			throw new SellerException("No Seller Found!");
		
		return sellers;
	}

	@Override
	public Seller getSellerById(Integer sellerId) throws SellerNotFoundException {
		
		Seller seller = sellerRepository.findById(sellerId)
				.orElseThrow(()->new SellerNotFoundException("Seller Does not Found with this Id : "+sellerId));
		
		return seller;
	}

	@Override
	public Seller getCurrentlyLoggedInSeller(String token) throws SellerNotFoundException {
		
		Seller seller = getSellerByToken(token);
		
		return seller;
	}

	@Override
	public SessionDTO updateSellerPassword(SellerDTO sellerDto, String token) throws SellerException {
		
		Seller seller = getSellerByToken(token);
		
		if(seller.getMobileNo().equals(sellerDto.getMobileNo())
				|| seller.getEmailId().equals(sellerDto.getEmailId())) {
			seller.setPassword(sellerDto.getPassword());
			sellerRepository.save(seller);
			authService.logoutSeller(token);
			return new SessionDTO(token,"Password Updated and Logged out. Again log in with new Password");
		}
		else
			throw new SellerException("Mobile No or Email Id does not match our Record!");
	}

	@Override
	public Seller updateSeller(Seller seller, String token) throws SellerException {
		
		Seller existingSeller = getSellerByToken(token);
		
		if(!existingSeller.getPassword().equals(seller.getPassword()))
			throw new SellerException("Password Incorrect!");
		
		if(seller.getFirstName()!=null)
			existingSeller.setFirstName(seller.getFirstName());
		
		if(seller.getLastName()!=null)
			existingSeller.setLastName(seller.getLastName());
		
		if(seller.getMobileNo()!=null)
			existingSeller.setMobileNo(seller.getMobileNo());
		
		if(seller.getEmailId()!=null)
			existingSeller.setEmailId(seller.getEmailId());
		
		return sellerRepository.save(existingSeller);
	}

	@Override
	public String deleteSellerById(Integer sellerId, String token) throws SellerException {
		
		Seller seller = getSellerByToken(token);
		
		if(!seller.getSellerId().equals(sellerId)) 
			throw new SellerException("Verification Error!");
		
		authService.logoutSeller(token);
		sellerRepository.delete(seller);
		
		return "Deleted Successfully.";
	}
	

}
