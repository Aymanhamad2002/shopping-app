package com.backendProject.shoppingApp.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendProject.shoppingApp.dto.RequestCategoryDto;
import com.backendProject.shoppingApp.exception.AlreadyExistsException;
import com.backendProject.shoppingApp.exception.CategoryNotFoundException;
import com.backendProject.shoppingApp.model.Category;
import com.backendProject.shoppingApp.response.ApiResponse;

import com.backendProject.shoppingApp.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

	private final ICategoryService categoryService;

	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Found", categories));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
		}
		
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody RequestCategoryDto request ){
		try {
			Category newCategory = categoryService.addCategory(request);
			return ResponseEntity.ok(new ApiResponse("created", newCategory));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	
	@GetMapping("/category/id/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long 	id){
		try {
			Category category = categoryService.getCategoryById(id);
			return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("found", category));
		} catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@GetMapping("/category/name/{name}")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		try {
			Category category = categoryService.getCategoryByName(name);
			return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("found", category));
		} catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@DeleteMapping("/category/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
		try {
			 categoryService.deleteCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("delete it successfuly", null));
		} catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@PutMapping("/category/update/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id , @RequestBody Category category){
		try {
			Category updatedCategory = categoryService.updateCategory(category,id);
			return ResponseEntity.ok(new ApiResponse("found", updatedCategory));
		} catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
		
		
	}
	

}
