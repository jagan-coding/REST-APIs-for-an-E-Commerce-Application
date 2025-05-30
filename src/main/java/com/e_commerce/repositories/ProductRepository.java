package com.e_commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e_commerce.models.Product;
import com.e_commerce.models.ProductDTO;
import com.e_commerce.models.Category;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query(
			nativeQuery = true,
			value = "SELECT product_name,price,manufacturer,quantity FROM Product WHERE category=:category")
	List<ProductDTO> getAllProductsInACategory(@Param("category") Category category);
	
	
	@Query(
			nativeQuery = true,
			value = "select product_name,price,manufacturer,quantity FROM Product WHERE status=:status")
	List<ProductDTO> getProductsWithStatus(@Param("status") String status);
	
	
	@Query(
			nativeQuery = true,
			value = "SELECT product_name,price,manufacturer,quantity FROM Product WHERE seller_seller_id=:id")
	List<ProductDTO> getProductsOfASeller(@Param("id") Integer id);
	
}
