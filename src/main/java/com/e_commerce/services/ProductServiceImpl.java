package com.e_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.ProductException;
import com.e_commerce.exceptions.ProductNotFoundException;
//import com.e_commerce.exceptions.SellerException;
import com.e_commerce.models.Category;
import com.e_commerce.models.Product;
import com.e_commerce.models.ProductDTO;
import com.e_commerce.models.Seller;
import com.e_commerce.repositories.ProductRepository;
import com.e_commerce.repositories.SellerRepository;
//import com.e_commerce.repositories.SellerRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	
	@Override
	public Product addProduct(Product product,String token) {
		
		Seller existingSeller = sellerService.getSellerByToken(token);
		
		product.setSeller(existingSeller);
		
		existingSeller.getProducts().add(product);
		
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(Integer productId) {
		
		Product product = productRepository.findById(productId)
				.orElseThrow(()->new ProductNotFoundException("Product Does not Found with this Id : "+productId));
		
		return product;
	}

	@Override
	public String deleteProduct(Integer productId,String token) {
		
		Seller seller = sellerService.getSellerByToken(token);
		
		Product product = productRepository.findById(productId)
				.orElseThrow(()->new ProductNotFoundException("Product Does not Found with this Id : "+productId));	
		
		if(!seller.getSellerId().equals(product.getSeller().getSellerId()))
			throw new ProductException("Unauthroized: You can only delete your own products!");
			
		List<Product> products = seller.getProducts();
		
		products.remove(product);
		product.setSeller(null);
		sellerRepository.save(seller);
		
		productRepository.delete(product);
		
		return "Deleted Successfully!";
		
	}

	@Override
	public Product updateProduct(Product updatedProduct,String token) {
		
		Seller seller = sellerService.getSellerByToken(token);
		
		Product product = productRepository.findById(updatedProduct.getProductId())
				.orElseThrow(()-> new ProductNotFoundException("Product Does Not Found!"));
		
		if(!seller.getSellerId().equals(product.getSeller().getSellerId()))
			throw new ProductException("Unauthroized: You can only update your own products!");
			
		if(updatedProduct.getProductName()!=null)
			product.setProductName(updatedProduct.getProductName());
		
		if(updatedProduct.getCategory()!=null)
			product.setCategory(updatedProduct.getCategory());
		
		if(updatedProduct.getDescripion()!=null)
			product.setDescripion(updatedProduct.getDescripion());
		
		if(updatedProduct.getManufacturer()!=null)
			product.setManufacturer(updatedProduct.getManufacturer());

		if(updatedProduct.getPrice()!=null)
			product.setPrice(updatedProduct.getPrice());

		if(updatedProduct.getQuantity()!=null)
			product.setQuantity(updatedProduct.getQuantity());

		if(updatedProduct.getStatus()!=null)
			product.setStatus(updatedProduct.getStatus());
	
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		
		List<Product> products =  productRepository.findAll();
		
		if(products.isEmpty())
			throw new ProductException("No Products!");
		return products;
	}

	@Override
	public List<ProductDTO> getProductsBySeller(Integer sellerId) {
		
		List<ProductDTO> products = productRepository.getProductsOfASeller(sellerId);
		
		if(products.isEmpty())
			throw new ProductException("No Products Found!");
		
		return products;
	}

	@Override
	public List<ProductDTO> getProductsByCategory(Category category) {
		
		List<ProductDTO> products = productRepository.getAllProductsInACategory(category);
		
		if(products.isEmpty())
			throw new ProductException("No Products Found!");
		
		return products;
	}

	@Override
	public List<ProductDTO> getProductsByStatus(String status) {
		
		List<ProductDTO> products = productRepository.getProductsWithStatus(status);
		
		System.out.println(products);
		
//		if(products.isEmpty())
//			throw new ProductException("No Products Found!");
		
		
		return products;
	}

}
