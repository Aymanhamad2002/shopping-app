package com.backendProject.shoppingApp.service.product;


import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backendProject.shoppingApp.dto.ImageDto;
import com.backendProject.shoppingApp.dto.ProductDto;
import com.backendProject.shoppingApp.dto.RequestProductDto;
import com.backendProject.shoppingApp.dto.RequestProductUpdateDto;
import com.backendProject.shoppingApp.exception.AlreadyExistsException;
import com.backendProject.shoppingApp.exception.CategoryNotFoundException;
import com.backendProject.shoppingApp.exception.ProductNotFoundException;
import com.backendProject.shoppingApp.model.Category;
import com.backendProject.shoppingApp.model.Image;
import com.backendProject.shoppingApp.model.Product;
import com.backendProject.shoppingApp.repository.category.CategoryRepository;
import com.backendProject.shoppingApp.repository.image.ImageRepository;
import com.backendProject.shoppingApp.repository.product.ProductRepository;
import com.backendProject.shoppingApp.utils.Messages;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	private final ImageRepository imageRepository;
	private final  ProductRepository productRepository;
	private final  CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	



	@Override
	public Product addProduct(RequestProductDto product) {
		//check if the category is found 
		// If yes , set it as the new product category
		// if Noe, then save it as a new category, then set it as the new product category
		if(productExist(product.getName(), product.getBrand())) {
			throw new AlreadyExistsException(product.getBrand() + " " + product.getName() + " already exists");
		}
		Category c = categoryRepository.findByName(product.getCategory().getName())
				.orElseGet(() -> {
					Category newCategory = Category
											.builder()
											.name(product.getCategory().getName())
											.build();
					return categoryRepository.save(newCategory);
				});
		product.setCategory(c);
		
		
		
		return productRepository.save(createProductHelper(product, c));
	}
	private Product   createProductHelper(RequestProductDto product, Category c) {
		Product toAddProduct = Product
				.builder()
				.name(product.getName())
				.brand(product.getBrand())
				.description(product.getDescription())
				.inventory(product.getInventory())
				.price(product.getPrice())
				.category(c)
				.build();
		
		return toAddProduct;
		
	}
	private boolean productExist(String name , String brand) {
		return productRepository.existsByNameAndBrand(name,brand);
	}


	@Override
	public Product getProductById(Long id) {
		
		return productRepository.findById(id).
				orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND));
	}


	@Override
	public void deleteProductById(Long id) {
		
		productRepository.findById(id)
			.ifPresentOrElse(productRepository::delete, 
					() ->{throw  new ProductNotFoundException(Messages.NOT_FOUND) ;});
		
	}


	@Override
	public Product updateProduct(RequestProductUpdateDto request, Long productId)
	{
		return productRepository.findById(productId)
								.map(product ->updateExistingProduct(product, request))
								.map(productRepository::save)
								.orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND));
								
		
		
	}
	
	private Product updateExistingProduct(Product existingProduct , RequestProductUpdateDto request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		
		existingProduct.setDescription(request.getDescription());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		Category c = categoryRepository.findByName(request.getCategory().getName()).orElseThrow(() -> new CategoryNotFoundException (Messages.CATEGORY_NOT_FOUND));
		
		existingProduct.setCategory(c);
		return existingProduct;
	}


	@Override
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}


	@Override
	public List<Product> getProductsByCategory(String category) {
		
		List<Product> products = productRepository.findByCategoryName(category);
		if(products.isEmpty()) {
			throw new ProductNotFoundException(Messages.NOT_FOUND);
		}else {
			return products;
		}
	}


	@Override
	public List<Product> getProductsByBrand(String brand) {
		
		List<Product>  products  = productRepository.findByBrand(brand);
		if(products.isEmpty()) {
			throw new ProductNotFoundException(Messages.NOT_FOUND);
		}else {
			return products;
		}
	}


	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		
		List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);
		if(products.isEmpty()) {
			throw new ProductNotFoundException(Messages.NOT_FOUND);
		}else {
			return products;
		}
	}


	@Override
	public List<Product> getProductsByName(String name) {
		
		List<Product> products =  productRepository.findByName(name);
		if(products.isEmpty()) {
			throw new ProductNotFoundException(Messages.NOT_FOUND);
		}else {
			return products;
		}
	}


	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		
		List<Product> products =  productRepository.findByBrandAndName(brand,name);
		if(products.isEmpty()) {
			throw new ProductNotFoundException(Messages.NOT_FOUND);
		}else {
			return products;
		}
				
	}


	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		
		return productRepository.countByBrandAndName(brand,name);
	}
	
	@Override
	public List<ProductDto> getConveretedProducts(List<Product> products){
		return products.stream()
				.map(product->convertToDto(product))
				.toList();
		
	}
	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imagesDto = images.stream()
				.map(image -> modelMapper.map(image, ImageDto.class))
				.collect(Collectors.toList());
		productDto.setImages(imagesDto);
		return productDto;
	}
	


}
