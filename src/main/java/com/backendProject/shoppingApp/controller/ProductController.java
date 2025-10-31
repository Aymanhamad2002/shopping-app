package com.backendProject.shoppingApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendProject.shoppingApp.dto.ProductDto;
import com.backendProject.shoppingApp.dto.RequestProductDto;
import com.backendProject.shoppingApp.dto.RequestProductUpdateDto;
import com.backendProject.shoppingApp.exception.AlreadyExistsException;
import com.backendProject.shoppingApp.exception.ProductNotFoundException;
import com.backendProject.shoppingApp.model.Product;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.service.product.IProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
	private final IProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		List<ProductDto> productsDto = productService.getConveretedProducts(products);
		return ResponseEntity.ok(new ApiResponse("returned all", productsDto));
	}
	@GetMapping("/product/id/{id}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
		try {
			ProductDto p = productService.convertToDto(productService.getProductById(id));
			return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("returned successfuly", p));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody RequestProductDto product){
		try {
			ProductDto createdProduct =productService.convertToDto(productService.addProduct(product));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse("created successfuly", createdProduct));
		}catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/product/update/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody RequestProductUpdateDto  request, @PathVariable Long   id){
		try{
			ProductDto updatedProduct =productService.convertToDto(productService.updateProduct(request, id));
			return ResponseEntity.ok(new ApiResponse("updated successfuly", updatedProduct));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		
		}		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long   id){
		try{
			productService.deleteProductById(id);
			return ResponseEntity.ok(new ApiResponse("delete it successfuly", null));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		
		}		
	}
	@GetMapping("/brand-and-name")
	public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
			List<ProductDto> productsDto = productService.getConveretedProducts(products);
			return ResponseEntity.ok(new ApiResponse("returned successfuly", productsDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		
		}	
		
	}
	
	@GetMapping("/category-and-brand")
	public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String brandName, @RequestParam String categoryName){
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(categoryName,brandName);
			List<ProductDto> productsDto = productService.getConveretedProducts(products);
			return ResponseEntity.ok(new ApiResponse("returned successfuly", productsDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		
		}	
		
	}
	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> products = productService.getProductsByName(name);
			List<ProductDto> productsDto = productService.getConveretedProducts(products);
			return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("returned successfuly", productsDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@GetMapping("/brand/{brand}")
	public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand){
		try {
			List<Product> products = productService.getProductsByBrand(brand);
			List<ProductDto> productsDto = productService.getConveretedProducts(products);
			return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("returned successfuly", productsDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@GetMapping("/category/{categoryName}")
	public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String categoryName){
		try {
			List<Product> products = productService.getProductsByCategory(categoryName);
			List<ProductDto> productsDto = productService.getConveretedProducts(products);
			return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("returned successfuly", productsDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	@GetMapping("/count/brand-and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
		try {
			long number = productService.countProductsByBrandAndName(brandName, productName);
			
			return ResponseEntity.ok(new ApiResponse("count returned successfuly", number));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		
		}	
			
	}
	
	
	
		
}









































