package com.e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
