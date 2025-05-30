package com.e_commerce.services;

import java.time.LocalDate;
import java.util.List;

import com.e_commerce.models.Customer;
import com.e_commerce.models.Order;
import com.e_commerce.models.OrderDTO;

public interface OrderService {
	
	public Order saveOrder(OrderDTO orderDto,String token);
	
	public Order getOrderByOrderId(Integer orderId);
	
	public List<Order> getAllOrders();
	
	public Order cancelOrderByOrderId(Integer orderId,String token);
	
	public List<Order> getAllOrdersByDate(LocalDate date);
	
	public Customer getCustomerByOrderId(Integer orderId);
	
}
