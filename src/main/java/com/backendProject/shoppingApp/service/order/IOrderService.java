package com.backendProject.shoppingApp.service.order;

import java.util.List;

import com.backendProject.shoppingApp.dto.OrderDto;
import com.backendProject.shoppingApp.model.Order;

public interface IOrderService {
	
	OrderDto placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrders(Long userId);

}
