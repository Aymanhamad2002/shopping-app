package com.backendProject.shoppingApp.service.category;

import java.util.List;

import com.backendProject.shoppingApp.dto.RequestCategoryDto;
import com.backendProject.shoppingApp.model.Category;


public interface ICategoryService {
	Category  getCategoryById(Long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();
	Category addCategory(RequestCategoryDto category);
	Category updateCategory(Category category ,Long id);
	void deleteCategoryById(Long id);

}
