package com.backendProject.shoppingApp.dto;

import java.math.BigDecimal;
import java.util.List;

import com.backendProject.shoppingApp.model.Category;

import lombok.Data;

@Data
public class ProductDto {

	private Long id ;
	private String name;
	private String brand;
	private String description;
	private int inventory;
	private BigDecimal price;
	private Category category;
	private List<ImageDto> images;

	

}
