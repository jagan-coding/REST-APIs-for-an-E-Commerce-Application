package com.e_commerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.e_commerce.models.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer>{

	Optional<Seller> findByMobileNo(String mobileNo);
	
	Optional<Seller> findByEmailId(String emailId);
	
}
