package com.backendProject.shoppingApp.dto;

import java.math.BigDecimal;

import com.backendProject.shoppingApp.model.Category;

import lombok.Data;


@Data
public class RequestProductUpdateDto {
	private String name;
	private String brand;
	private String description;
	private int inventory;
	private BigDecimal price;
	private Category category;

}
