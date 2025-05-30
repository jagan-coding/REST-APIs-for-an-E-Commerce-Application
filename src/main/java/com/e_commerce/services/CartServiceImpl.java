package com.e_commerce.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.CartException;
import com.e_commerce.exceptions.ProductNotFoundException;
import com.e_commerce.models.Cart;
import com.e_commerce.models.CartDTO;
import com.e_commerce.models.CartItem;
import com.e_commerce.models.Customer;
import com.e_commerce.models.Product;
import com.e_commerce.repositories.CartItemRepository;
import com.e_commerce.repositories.CartRepository;
import com.e_commerce.repositories.ProductRepository;


@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Cart addProductToCart(CartDTO cartDto, String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		
		Cart customerCart = customer.getCustomerCart();
		
		if(customerCart.getCartTotal()== null) {
			customerCart.setCartTotal(0.0);		
		}
		
		List<CartItem> cartItems = customerCart.getCartItems();
		
		Product product = productRepository.findById(cartDto.getProductId())
				.orElseThrow(()->new ProductNotFoundException("Product Does not found!"));		
		Optional<CartItem> opt = cartItemRepository.findItemInCart(customerCart.getCartId(),product.getProductId());
		
		if(opt.isPresent()) {
			CartItem existingItem = opt.get();
			existingItem.setCartItemQuantity(existingItem.getCartItemQuantity()+1);
			customerCart.setCartTotal(customerCart.getCartTotal()+existingItem.getCartProduct().getPrice());
			
			cartItemRepository.save(existingItem);
		}else {
			CartItem newItem = cartItemService.createItemFromCart(cartDto);
			newItem.setCart(customerCart);
			cartItems.add(newItem);
			customerCart.setCartTotal(customerCart.getCartTotal()+newItem.getCartProduct().getPrice());
			cartItemRepository.save(newItem);
		}
		customerCart.setCartItems(cartItems);
		return cartRepository.save(customerCart);
	}

	@Override
	public Cart getCartProduct(String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		
		Cart cart = customer.getCustomerCart();
		
		if(cart == null)
			throw new CartException("Cart not found for this Customer");
		
		if(cart.getCartItems().isEmpty())
			throw new CartException("Empty Cart!");
		
		return cart;
	}

	@Override
	public Cart removeProductFromCart(CartDTO cartDto, String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		Cart customerCart = customer.getCustomerCart();
		List<CartItem> cartItems = customerCart.getCartItems();
		
		boolean itemFound = false;
		CartItem itemToRemove = null;
		
		for(CartItem item:cartItems) {
			
			if(item.getCartProduct().getProductId().equals(cartDto.getProductId())) {
				
				item.setCartItemQuantity(item.getCartItemQuantity() - 1);
				customerCart.setCartTotal(customerCart.getCartTotal() - item.getCartProduct().getPrice());
				
				if(item.getCartItemQuantity() == 0) {
					
					itemToRemove = item;
	
				}
				
				itemFound = true;
			}
		}
		
		if(itemToRemove != null) {
			cartItems.remove(itemToRemove);
			cartItemRepository.delete(itemToRemove);
		}
		
		if(!itemFound)
			throw new CartException("Product not added into Cart!");
		
		return cartRepository.save(customerCart);
	}

	@Override
	public Cart updateCartQuantityInCart(CartDTO cartDto, Integer Newquantity, String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		Cart customerCart = customer.getCustomerCart();
		List<CartItem> cartItems = customerCart.getCartItems();
		
		boolean itemFound = false;
		
		for(CartItem item:cartItems) {
			if(item.getCartProduct().getProductId().equals(cartDto.getProductId())) {
				
				int aldready = item.getCartItemQuantity();
				
				item.setCartItemQuantity(Newquantity);
				
				customerCart.setCartTotal(customerCart.getCartTotal() 
						+ (item.getCartItemQuantity() * item.getCartProduct().getPrice()) - (aldready * item.getCartProduct().getPrice()));
					
				if(Newquantity == 0) {
					throw new CartException("Quantity must be greater than 0.)");
				}
				
				itemFound = true;
			}
		}
		
		if(!itemFound)
			throw new CartException("Product not added into Cart!");
		
		return cartRepository.save(customerCart);
	}
	
//	@Transactional
	@Override
	public Cart clearCart(String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		Cart customerCart = customer.getCustomerCart();
		
		
		customerCart.getCartItems().clear();
		customerCart.setCartTotal(0.0);
		
		return cartRepository.save(customerCart);
	}

}
