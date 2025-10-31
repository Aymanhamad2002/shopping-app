package com.backendProject.shoppingApp.dto;

import java.math.BigDecimal;
import java.util.List;

import com.backendProject.shoppingApp.model.Category;
import com.backendProject.shoppingApp.model.Image;

import lombok.Builder;
import lombok.Data;




@Data
@Builder

public class RequestProductDto {
	private String name;
	private String brand;
	private String description;
	private int inventory;
	private BigDecimal price;
	private Category category;

	

}
