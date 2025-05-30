package com.e_commerce.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.models.Product;
import com.e_commerce.models.ProductDTO;
import com.e_commerce.services.ProductService;
import com.e_commerce.models.Category;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping(value="/api/add/prod")
	public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestHeader String token){
		return new ResponseEntity<>(productService.addProduct(product, token),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id){
		return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/prod")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,@RequestHeader String token){
		return new ResponseEntity<>(productService.updateProduct(product, token),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/prod/sel/{id}")
	public ResponseEntity<List<ProductDTO>> getProductsBySeller(@PathVariable("id") Integer sellerId){
		return new ResponseEntity<>(productService.getProductsBySeller(sellerId),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/prod/category/{category}")
	public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable("category") String category){
		
		Category enumCategory = Category.valueOf(category.toUpperCase());
		List<ProductDTO> products = productService.getProductsByCategory(enumCategory);
		return new ResponseEntity<>(products,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/api/prod")
	public ResponseEntity<List<ProductDTO>> getProductsByStatus(@RequestParam String status){
		
//		ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
		List<ProductDTO> products = productService.getProductsByStatus(status);
		return new ResponseEntity<>(products,HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="/api/prod/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer productId,@RequestHeader String token){
		
		productService.deleteProduct(productId, token);
		return new ResponseEntity<>("Product Deleted Successfully.",HttpStatus.OK);
		
	}
}
