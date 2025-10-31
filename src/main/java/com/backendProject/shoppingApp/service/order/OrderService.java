package com.backendProject.shoppingApp.service.order;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.backendProject.shoppingApp.dto.OrderDto;
import com.backendProject.shoppingApp.enums.OrderStatus;
import com.backendProject.shoppingApp.exception.OrderNotFoundException;
import com.backendProject.shoppingApp.model.Cart;
import com.backendProject.shoppingApp.model.Order;
import com.backendProject.shoppingApp.model.OrderItem;
import com.backendProject.shoppingApp.model.Product;
import com.backendProject.shoppingApp.repository.order.OrderRepository;
import com.backendProject.shoppingApp.repository.product.ProductRepository;
import com.backendProject.shoppingApp.service.cart.ICartService;
import com.backendProject.shoppingApp.utils.Messages;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class OrderService  implements IOrderService{

    private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final ICartService cartService;
	private final ModelMapper modelMapper;



	
	
	
	

	@Override
	public OrderDto placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);
		Set<OrderItem>orderItemlist = createOrderItems(order, cart);
		order.setOrderItems(orderItemlist);
		order.setOrderTotalAmount(calculateTotalAmount(orderItemlist));
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(cart.getId());
		return convertToDto(savedOrder);

	}
	
	private Order createOrder(Cart cart ) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	
	private  Set<OrderItem> createOrderItems(Order order, Cart cart){
		return cart.getCartItems()
				.stream()
				.map(cartItem -> {
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantity());
			productRepository.save(product);
			return OrderItem
					.builder()
					.order(order)
					.product(product)
					.quantity(cartItem.getQuantity())
					.price(cartItem.getUnitPrice())
					.build();
		}).collect(Collectors.toSet());
		
	}
	
	
	private BigDecimal calculateTotalAmount(Set<OrderItem> orderItemList) {
		return orderItemList
				.stream()
				.map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
	}
	
	
	@Override
	public  OrderDto getOrder(Long orderId) {
		
		return orderRepository.findById(orderId)
				.map(order -> convertToDto(order))
				.orElseThrow(()-> new OrderNotFoundException(Messages.ORDER_NOTFOUND));
	}
	
	@Override
	public List<OrderDto> getUserOrders(Long userId){
		return orderRepository.findByUserId(userId)
				.stream()
				.map(order -> convertToDto(order))
				.toList();
	}
	
	
	private OrderDto convertToDto(Order order) {
		return modelMapper.map(order, OrderDto.class);
	}

}
