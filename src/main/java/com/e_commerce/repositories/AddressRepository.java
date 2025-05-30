package com.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
