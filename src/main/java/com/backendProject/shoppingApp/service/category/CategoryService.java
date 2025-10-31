package com.backendProject.shoppingApp.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backendProject.shoppingApp.model.Category;
import com.backendProject.shoppingApp.repository.category.CategoryRepository;
import com.backendProject.shoppingApp.utils.Messages;
import com.backendProject.shoppingApp.dto.RequestCategoryDto;
import com.backendProject.shoppingApp.exception.AlreadyExistsException;
import com.backendProject.shoppingApp.exception.CategoryNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
	private final CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException (Messages.CATEGORY_NOT_FOUND));
	}

	@Override
	public Category getCategoryByName(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException (Messages.CATEGORY_NOT_FOUND));
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(RequestCategoryDto category) {
		return Optional.of(category).filter(c-> !categoryRepository.existsByName(category.getName()))
				.map(c->categoryRepository.save(Category.builder().name(category.getName()).build()))
				.orElseThrow(() -> new AlreadyExistsException(category.getName() + " " +Messages.ALREADY_EXISTS));
		
	}

	@Override
	public Category updateCategory(Category category,Long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow(() -> new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND));
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, ()-> {
			throw new CategoryNotFoundException(Messages.CATEGORY_NOT_FOUND);
		});
		
	}

}
