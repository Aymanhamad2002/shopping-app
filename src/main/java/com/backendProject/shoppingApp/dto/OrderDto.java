package com.backendProject.shoppingApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import lombok.Data;

@Data
public class OrderDto {
	private Long orderId;
	private	Long userId;
	private LocalDate orderDate;
	private BigDecimal orderTotalAmount;
	private String orderStatus;
	private List<OrderItemDto> items;
	
}
