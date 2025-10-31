package com.backendProject.shoppingApp.service.product;

import java.util.List;

import com.backendProject.shoppingApp.dto.ProductDto;
import com.backendProject.shoppingApp.dto.RequestProductDto;
import com.backendProject.shoppingApp.dto.RequestProductUpdateDto;
import com.backendProject.shoppingApp.model.Product;

public interface IProductService {
	Product addProduct(RequestProductDto product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(RequestProductUpdateDto product,Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	Long countProductsByBrandAndName(String brand, String name);
	ProductDto convertToDto(Product product);
	List<ProductDto> getConveretedProducts(List<Product> products);
	
	
	

}
