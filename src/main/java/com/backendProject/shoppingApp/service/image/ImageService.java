package com.backendProject.shoppingApp.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.backendProject.shoppingApp.dto.ImageDto;
import com.backendProject.shoppingApp.exception.ImageNotFoundException;
import com.backendProject.shoppingApp.model.Image;
import com.backendProject.shoppingApp.model.Product;
import com.backendProject.shoppingApp.repository.image.ImageRepository;
import com.backendProject.shoppingApp.service.product.IProductService;
import com.backendProject.shoppingApp.utils.Messages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService  implements IImageService{

	private final ImageRepository imageRepository;
	private final IProductService productService;



	@Override
	public Image getImageById(Long id) {
		// TODO Auto-generated method stub
		return imageRepository.findById(id)
				.orElseThrow(() -> new ImageNotFoundException(Messages.IMAGE_NOT_FOUND));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id)
		.ifPresentOrElse(imageRepository::delete,() -> {
			throw new ImageNotFoundException(Messages.IMAGE_NOT_FOUND);
		});
		
	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);
		List<ImageDto> savedImagesDto = new ArrayList<>();
		for(MultipartFile file : files) {
			try {
			Image image = Image.builder()
							.fileName(file.getOriginalFilename())
							.fileType(file.getContentType())
							.images(new SerialBlob(file.getBytes()))
							.product(product)
							.build();
			String downloadUrl = "/api/v1/images/image/download/" + image.getId();
			image.setDownloadUrl(downloadUrl);
			Image savedImage  = imageRepository.save(image);
			savedImage.setDownloadUrl("/api/v1/images/image/download/" + savedImage.getId());
			savedImage = imageRepository.save(savedImage);
			ImageDto imageDto = new ImageDto();
			imageDto.setImageId(savedImage.getId());
			imageDto.setImageName(savedImage.getFileName());
			imageDto.setDownloadUrl(savedImage.getDownloadUrl());
			savedImagesDto.add(imageDto);
			}catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e.getMessage());
			}			
		}
		
		return savedImagesDto;
	}

	@Override
	public void updateImage(MultipartFile file, long imageId) {
		Image image = getImageById(imageId);
		image.setFileName(file.getOriginalFilename());
		try {
			image.setImages(new SerialBlob(file.getBytes()));
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		imageRepository.save(image);
		
	}

}
