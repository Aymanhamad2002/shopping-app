package com.backendProject.shoppingApp.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.backendProject.shoppingApp.dto.ImageDto;
import com.backendProject.shoppingApp.exception.ImageNotFoundException;
import com.backendProject.shoppingApp.model.Image;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
	private final IImageService imageService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam("file") List<MultipartFile> files, @RequestParam Long productId){
		 try {
	            List<ImageDto> imagesDtos = imageService.saveImages(files, productId);
	            return ResponseEntity.ok(new ApiResponse("Uploaded success", imagesDtos));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new ApiResponse("Upload failed!", e.getMessage()));
	        }
	}
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) throws SQLException{
		
		// this how to return an image in rest api
		Image image = imageService.getImageById(imageId);
		
		ByteArrayResource resource = new ByteArrayResource(image.getImages().getBytes(1, (int) image.getImages().length()));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+image.getFileName()+"\"")
				.body(resource);
		
		
	}
	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam  MultipartFile file){
		try{
			
		imageService.updateImage(file, imageId);
		return ResponseEntity.ok(new ApiResponse("Update sucess ", null));
		}catch(ImageNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()	, null));
			
		}
		
	}
	@DeleteMapping("/image/{imageId}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
		try{
			imageService.deleteImageById( imageId);
			return ResponseEntity.ok(new ApiResponse("delete sucess ", null));
		}catch(ImageNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()	, null));
			
		}
		
	}

}
