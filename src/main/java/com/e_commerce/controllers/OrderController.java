package com.e_commerce.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.models.Customer;
import com.e_commerce.models.Order;
import com.e_commerce.models.OrderDTO;
import com.e_commerce.services.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	public OrderService orderService;
	
	@PostMapping(value="/api/ord")
	public ResponseEntity<Order> saveOrder(@RequestBody OrderDTO orderDto,@RequestHeader String token){
		return new ResponseEntity<>(orderService.saveOrder(orderDto, token),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/ord/{id}")
	public ResponseEntity<Order> getOrderByOrderId(@PathVariable("id") Integer orderId){
		return new ResponseEntity<>(orderService.getOrderByOrderId(orderId),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/orders")
	public ResponseEntity<List<Order>> getAllOrders(){
		return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
	}
	
	@PutMapping(value="/api/ord/cancel/{id}")
	public ResponseEntity<Order> cancelOrderByOrderId(@PathVariable("id") Integer orderId,@RequestHeader String token){
		return new ResponseEntity<>(orderService.cancelOrderByOrderId(orderId, token),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/ord/date")
	public ResponseEntity<List<Order>> getAllOrdersByDate(@RequestParam("date") String dateStr){
		
		LocalDate date = LocalDate.parse(dateStr);
		return new ResponseEntity<>(orderService.getAllOrdersByDate(date),HttpStatus.OK);
	}
	
	@GetMapping(value="/api/cus/ord/{id}")
	public ResponseEntity<Customer> getCustomerByOrderId(@PathVariable("id") Integer orderId){
		return new ResponseEntity<>(orderService.getCustomerByOrderId(orderId),HttpStatus.OK);
	}
		
}
