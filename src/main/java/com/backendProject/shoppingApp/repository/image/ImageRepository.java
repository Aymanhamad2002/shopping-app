package com.backendProject.shoppingApp.repository.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.Image;

public interface ImageRepository  extends JpaRepository<Image,Long>{

	List<Image> findByProductId(Long id);

}
