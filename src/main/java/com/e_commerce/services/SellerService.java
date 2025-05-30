package com.e_commerce.services;

import java.util.List;

import com.e_commerce.exceptions.SellerException;
import com.e_commerce.exceptions.SellerNotFoundException;
import com.e_commerce.models.Seller;
import com.e_commerce.models.SellerDTO;
import com.e_commerce.models.SessionDTO;

public interface SellerService {
	
//	Basic Seller Operations
	
	public Seller addSeller(Seller seller);
	
	public List<Seller> getAllSellers() throws SellerException;
	
	public Seller getSellerByToken(String token);
	
	public Seller getSellerById(Integer sellerId) throws SellerNotFoundException;
	
//	public Seller getSellerByMobileNo(String mobileNo) throws SellerNotFoundException;
	
	public Seller getCurrentlyLoggedInSeller(String token)throws SellerNotFoundException;
	
//	Update Seller Operations
	
	public SessionDTO updateSellerPassword(SellerDTO sellerDto,String token) throws SellerException;
	
	public Seller updateSeller(Seller seller,String token)throws SellerException;
		
//	Delete Seller
	
	public String deleteSellerById(Integer sellerId,String token)throws SellerException;
}
