package com.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cartItemId;
	
	@OneToOne
	@JsonIgnoreProperties(value= {
			"productId",
			"seller",
			"quantity"
	})
	private Product cartProduct;
	
	@ManyToOne
	@JoinColumn(name="cart_id")
	@JsonIgnore
	private Cart cart;
	
	
	
	private Integer cartItemQuantity;

}
