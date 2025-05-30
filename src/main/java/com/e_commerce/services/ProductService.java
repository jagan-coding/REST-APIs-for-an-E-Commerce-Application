package com.e_commerce.services;

import java.util.List;

import com.e_commerce.models.Category;
import com.e_commerce.models.Product;
import com.e_commerce.models.ProductDTO;

public interface ProductService {

//	Add product
	
	public Product addProduct(Product product,String token);
	
//	Getting Product Operations
	
	public Product getProductById(Integer productId);
	
	public List<Product> getAllProducts();
	
//	Update Product Operations
	
	public Product updateProduct(Product product,String token);
	
//	Special Operations

	public List<ProductDTO> getProductsBySeller(Integer sellerId);
	
	public List<ProductDTO> getProductsByCategory(Category category);
	
	public List<ProductDTO> getProductsByStatus(String status);
	
//	Delete Product
	
	public String deleteProduct(Integer productId,String token);
	
}
