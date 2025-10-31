package com.backendProject.shoppingApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendProject.shoppingApp.dto.OrderDto;
import com.backendProject.shoppingApp.exception.CartNotFoundException;
import com.backendProject.shoppingApp.exception.OrderNotFoundException;
import com.backendProject.shoppingApp.model.Order;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	private final IOrderService orderService;
	
	
	@PostMapping("/order/userId/{userId}")
	public ResponseEntity<ApiResponse> createOrder(@PathVariable Long  userId){
		try {
				OrderDto order = orderService.placeOrder(userId);
				return ResponseEntity.ok(new ApiResponse("order placed successfuly", order));
		
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long  orderId){
		try {
				OrderDto order = orderService.getOrder(orderId);
				return ResponseEntity.ok(new ApiResponse("order returned successfuly", order));
		
		} catch (OrderNotFoundException e ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/userId/{userId}")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long  userId){
		try {
				List<OrderDto> orders = orderService.getUserOrders(userId);
				return ResponseEntity.ok(new ApiResponse("order returned successfuly", orders));
		
		} catch (OrderNotFoundException e ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
	
	
	
}
