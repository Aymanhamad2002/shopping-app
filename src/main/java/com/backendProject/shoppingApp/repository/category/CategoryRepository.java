package com.backendProject.shoppingApp.repository.category;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);

	boolean existsByName(String name);

}
