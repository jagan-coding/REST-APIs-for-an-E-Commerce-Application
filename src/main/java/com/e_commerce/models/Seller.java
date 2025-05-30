package com.e_commerce.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sellerId;
	private String firstName;
	private String lastName;
	private String password;
	
	@Column(unique = true,name = "mobile_no")
	private String mobileNo;
	
	@Column(unique = true)
	private String emailId;
	
	@OneToMany
	@JsonIgnore
	private List<Product> products;

}
