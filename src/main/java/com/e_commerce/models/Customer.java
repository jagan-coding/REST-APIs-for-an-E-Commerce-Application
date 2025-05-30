package com.e_commerce.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	
	private String firstName;
	private String lastName;
	
	@Column(unique=true)
	private String mobileNo;
	
	@Column(unique=true)
	private String emailId;
	
	private String password;
	private LocalDateTime createdOn;
	
	@Embedded
	private CreditCard creditCard;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="customer_address_mapping", 
								joinColumns = {
								@JoinColumn(name ="customer_id",referencedColumnName = "customerId")
								}, inverseJoinColumns = 
								{
								@JoinColumn(name="address_id",referencedColumnName = "addressId")
								})
	private Map<String, Address> address = new HashMap<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cart customerCart;
}
