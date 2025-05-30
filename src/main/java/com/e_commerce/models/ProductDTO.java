package com.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String prodName;
	private String manufacturer;
	private Double price;
	private Integer quantity;
}

//public interface ProductDTO {
//	
//    String getProductName();
//    Double getPrice();
//    String getManufacturer();
//    Integer getQuantity();
//    
//}
