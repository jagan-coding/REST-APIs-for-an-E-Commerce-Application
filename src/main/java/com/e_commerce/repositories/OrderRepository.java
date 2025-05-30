package com.e_commerce.repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e_commerce.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	@Query(
			nativeQuery = true,
			value = "select * from orders where date=:date")
	public List<Order> getAllOrdersByDate(@Param("date") LocalDate date);
	
	
//	@Query("SELECT o.customer FROM Order o WHERE o.orderId = :orderId")
//	public Customer getCustomerByOrderId(@Param("orderId") Integer orderId);
	
}
