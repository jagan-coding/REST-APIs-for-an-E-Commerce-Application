package com.e_commerce.services;

import com.e_commerce.models.CartDTO;
import com.e_commerce.models.CartItem;

public interface CartItemService {
	
	public CartItem createItemFromCart(CartDTO cartDto);

}
