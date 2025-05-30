package com.e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.models.Cart;
import com.e_commerce.models.CartDTO;
import com.e_commerce.services.CartService;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping(value="/api/cart/add")
	public ResponseEntity<Cart> addProductToCart(@RequestBody CartDTO cartDto,@RequestHeader String token){
		return new ResponseEntity<>(cartService.addProductToCart(cartDto, token),HttpStatus.CREATED);
	}
	
	@GetMapping(value="/api/cart")
	public ResponseEntity<Cart> getCartProduct(@RequestHeader String token){
		return new ResponseEntity<>(cartService.getCartProduct(token),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/api/cart")
	public ResponseEntity<Cart> removeProductFromCart(@RequestBody CartDTO cartDto,@RequestHeader String token){
		return new ResponseEntity<>(cartService.removeProductFromCart(cartDto, token),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/cart/update")
	public ResponseEntity<Cart> updateProductQuantityInCart(@RequestBody CartDTO cartDto,@RequestParam("quantity") Integer newQuantity, @RequestHeader String token){
		return new ResponseEntity<>(cartService.updateCartQuantityInCart(cartDto, newQuantity, token),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/api/cart/clear")
	public ResponseEntity<Cart> clearCart(@RequestHeader String token){
		return new ResponseEntity<>(cartService.clearCart(token),HttpStatus.OK);
	}
	
}
