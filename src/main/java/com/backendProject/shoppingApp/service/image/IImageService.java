package com.backendProject.shoppingApp.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backendProject.shoppingApp.dto.ImageDto;
import com.backendProject.shoppingApp.model.Image;

public interface IImageService {
	Image getImageById(Long id);
	void deleteImageById(Long id);
	 List<ImageDto>  saveImages(List<MultipartFile> file , Long productId);
	void updateImage(MultipartFile file ,long imageId);
}
