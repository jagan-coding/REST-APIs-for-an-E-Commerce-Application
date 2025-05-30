package com.e_commerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.exceptions.CartException;
import com.e_commerce.exceptions.CustomerNotFoundException;
import com.e_commerce.exceptions.OrderException;
import com.e_commerce.models.Cart;
import com.e_commerce.models.CartItem;
import com.e_commerce.models.CreditCard;
import com.e_commerce.models.Customer;
import com.e_commerce.models.Order;
import com.e_commerce.models.OrderDTO;
import com.e_commerce.models.OrderItem;
import com.e_commerce.models.OrderStatusValues;
import com.e_commerce.repositories.CartRepository;
import com.e_commerce.repositories.CustomerRepository;
import com.e_commerce.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Order saveOrder(OrderDTO orderDto, String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		
		Cart customerCart = customer.getCustomerCart();
		
		List<CartItem> cartItems = customerCart.getCartItems();
		
		if(cartItems.isEmpty())
			throw new CartException("Empty Cart!");
		
		CreditCard card = customer.getCreditCard();
		CreditCard dtoCard = orderDto.getCardNumber();
		
		
		
		if(!card.getCardNumber().equals(dtoCard.getCardNumber())
				|| !card.getCardValidity().equals(dtoCard.getCardValidity())
				|| !card.getCardCVV().equals(dtoCard.getCardCVV()))
			throw new OrderException("Wrong Credit card Details!");
		
		Order order = new Order();
		order.setCustomer(customer);
		order.setAddress(customer.getAddress().get(orderDto.getAddressType()));
		order.setDate(LocalDate.now());
		order.setOrderStatus(OrderStatusValues.SUCCESS);
		order.setCardNumber(orderDto.getCardNumber().getCardNumber());
		
		List<OrderItem> orderItems = new ArrayList<>();
		double total = 0.0;
		
		for(CartItem item:cartItems) {
			
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(item.getCartProduct());
			orderItem.setQuantity(item.getCartItemQuantity());
			double itemTotal = item.getCartProduct().getPrice() * item.getCartItemQuantity();
			orderItem.setPrice(itemTotal);
			orderItem.setOrder(order);
			
			orderItems.add(orderItem);
			total=total+itemTotal;
		}
		
		order.setOrderItems(orderItems);
		order.setTotal(total);
		
		Order savedOrder = orderRepository.save(order);
		
		cartItems.clear();
		customerCart.setCartTotal(0.0);
		cartRepository.save(customerCart);
		
		return savedOrder;
		
	}

	@Override
	public Order getOrderByOrderId(Integer orderId) {
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()-> new OrderException("Order Does not Found with this Id : "+orderId));
		
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		
		List<Order> orders = orderRepository.findAll();
		
		if(orders.isEmpty())
			throw new OrderException("No Orders Found!");
		
		return orders;
	}

	@Override
	public Order cancelOrderByOrderId(Integer orderId, String token) {
		
		Customer customer = customerService.getCustomerByToken(token);
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()-> new OrderException("Order Does not Found with this Id : "+orderId));
			
		if(!customer.getCustomerId().equals(order.getCustomer().getCustomerId()))
			throw new OrderException("You are not AUTHORIZED to cancel this Order!");
		
		if(order.getOrderStatus() == OrderStatusValues.PENDING) {
			
			order.setOrderStatus(OrderStatusValues.CANCELLED);
		
		}
		else
			throw new OrderException("Only PENDING Orders can be Cancellable...");
		
		
		return orderRepository.save(order);
		
	}
	
	@Override
	public List<Order> getAllOrdersByDate(LocalDate date) {
		
		List<Order> orders =  orderRepository.getAllOrdersByDate(date);
		
		return orders;
	}

	@Override
	public Customer getCustomerByOrderId(Integer orderId) {
		
		Order customerOrder = orderRepository.findById(orderId)
				.orElseThrow(()-> new OrderException("Order Not Found with this ID : "+orderId));
		
		Customer customer = customerRepository.findById(customerOrder.getCustomer().getCustomerId())
				.orElseThrow(()-> new CustomerNotFoundException("Customer Does not Found!"));
		
		return customer;
	}

}
