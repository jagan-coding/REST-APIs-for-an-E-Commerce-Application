package com.e_commerce.services;

import com.e_commerce.models.Cart;
import com.e_commerce.models.CartDTO;

public interface CartService {

	public Cart addProductToCart(CartDTO cartDto,String token);
	
	public Cart getCartProduct(String token);
	
	public Cart removeProductFromCart(CartDTO cartDto,String token);
	
	public Cart updateCartQuantityInCart(CartDTO cartDto,Integer Newquantity,String token);
	
	public Cart clearCart(String token);
	
}
