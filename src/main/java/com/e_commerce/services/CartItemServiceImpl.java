package com.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.ProductException;
import com.e_commerce.exceptions.ProductNotFoundException;
import com.e_commerce.models.CartDTO;
import com.e_commerce.models.CartItem;
import com.e_commerce.models.Product;
import com.e_commerce.models.ProductStatus;
import com.e_commerce.repositories.ProductRepository;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public CartItem createItemFromCart(CartDTO cartDto) {
		
		Product product = productRepository.findById(cartDto.getProductId())
				.orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));
		
		if(product.getStatus().equals(ProductStatus.OUTOFSTOCK) || product.getQuantity() == 0)
			throw new ProductException("This Product is OUT OF STOCK!");
		
		CartItem cartItem = new CartItem();
		
		cartItem.setCartProduct(product);
		cartItem.setCartItemQuantity(1);
		
		return cartItem;
	}

}
