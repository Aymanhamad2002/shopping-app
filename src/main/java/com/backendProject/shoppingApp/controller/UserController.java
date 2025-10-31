package com.backendProject.shoppingApp.controller;

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

import com.backendProject.shoppingApp.dto.RequestUserCreateDto;
import com.backendProject.shoppingApp.dto.RequestUserUpateDto;
import com.backendProject.shoppingApp.dto.UserDto;
import com.backendProject.shoppingApp.exception.UserNotFoundException;
import com.backendProject.shoppingApp.model.User;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
	private final IUserService userService;
	
	@GetMapping("/user/{id}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long  id ){
		try {
			User user = userService.getUserById(id);
			UserDto userDto = userService.convertToDto(user);
			return ResponseEntity.ok(new ApiResponse("user returned successfuly", userDto));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			
		}
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createUser(@RequestBody RequestUserCreateDto request ){
		try {
			User user = userService.createUser(request);
			UserDto userDto = userService.convertToDto(user);
			return ResponseEntity.ok(new ApiResponse("user created successfuly", userDto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
			
		}
		
	}
	
	@PutMapping("/user/update/{id}")
	public ResponseEntity<ApiResponse> updateUserById(@RequestBody RequestUserUpateDto request ,
			@PathVariable Long  id ){
		try {
			User user = userService.updateUser(request, id);
			UserDto userDto = userService.convertToDto(user);
			return ResponseEntity.ok(new ApiResponse("user updated successfuly", userDto));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			
		}
		
	}
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long  id ){
		try {
			 userService.deleteUser(id);
			return ResponseEntity.ok(new ApiResponse(" user deleted  successfuly", null));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
			
		}
		
	}
	
	
	
 
}
