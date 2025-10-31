package com.backendProject.shoppingApp.service.user;

import com.backendProject.shoppingApp.dto.RequestUserCreateDto;
import com.backendProject.shoppingApp.dto.RequestUserUpateDto;
import com.backendProject.shoppingApp.dto.UserDto;
import com.backendProject.shoppingApp.model.User;

public interface IUserService {
	User getUserById(Long userId);
	User createUser(RequestUserCreateDto request );
	User updateUser(RequestUserUpateDto request, Long userId);
	void deleteUser(Long userId);
	UserDto convertToDto(User user);
	User getAuthenticatedUser();
	

}
