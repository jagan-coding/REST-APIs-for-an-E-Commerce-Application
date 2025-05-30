package com.e_commerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e_commerce.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	
	
	@Query(
			nativeQuery = true,
			value="select * from cart_item where cart_id=:cartId and cart_product_product_id=:productId"
			)
	Optional<CartItem> findItemInCart(@Param("cartId") Integer cartId,@Param("productId") Integer productId);

//	@Modifying
//	@Query(
//			nativeQuery = true,
//			value="delete from cart_item where cart_id=:cartId"
//			)
//	public void deleteCartItem(@Param("cartId") Integer cartId);
}	
