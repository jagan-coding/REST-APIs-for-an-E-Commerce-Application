package com.e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.exceptions.SellerNotFoundException;
import com.e_commerce.models.Seller;
import com.e_commerce.models.SellerDTO;
import com.e_commerce.models.SessionDTO;
import com.e_commerce.repositories.SellerRepository;
import com.e_commerce.services.SellerService;

@RestController
public class SellerController {

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@GetMapping(value="/api/sel/all")
	public ResponseEntity<List<Seller>> getAllSellers(){
		return new ResponseEntity<>(sellerService.getAllSellers(),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/sel/current")
	public ResponseEntity<Seller> getCurrentlyLoggedInSeller(@RequestHeader String token){
		return new ResponseEntity<>(sellerService.getCurrentlyLoggedInSeller(token),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/sel/{id}")
	public ResponseEntity<Seller> getSellerById(@PathVariable("id") Integer sellerId){
		return new ResponseEntity<>(sellerService.getSellerById(sellerId),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/sel/mobile")
	public ResponseEntity<Seller> getSellerByMobileNo(@RequestBody SellerDTO sellerDto){
		Seller seller = sellerRepository.findByMobileNo(sellerDto.getMobileNo())
				.orElseThrow(()-> new SellerNotFoundException("Seller Does not Found!"));
		
		return new ResponseEntity<>(seller,HttpStatus.OK);
		
	}
	
	@PutMapping(value="/api/sel/upd/password")
	public ResponseEntity<SessionDTO> updateSellerPassword(@RequestBody SellerDTO sellerDto, @RequestHeader String token){
		return new ResponseEntity<>(sellerService.updateSellerPassword(sellerDto, token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/sel")
	public ResponseEntity<Seller> updateSeller(@RequestBody Seller seller,@RequestHeader String token){
		return new ResponseEntity<>(sellerService.updateSeller(seller, token),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/api/sel/{id}")
	public ResponseEntity<String> deleteSellerById(@PathVariable("id") Integer sellerId,@RequestHeader String token){
		sellerService.deleteSellerById(sellerId, token);
		return new ResponseEntity<>("Seller Deleted Successfully.",HttpStatus.OK);
	}
}
