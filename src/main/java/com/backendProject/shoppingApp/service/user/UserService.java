package com.backendProject.shoppingApp.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.backendProject.shoppingApp.dto.RequestUserCreateDto;
import com.backendProject.shoppingApp.dto.RequestUserUpateDto;
import com.backendProject.shoppingApp.dto.UserDto;
import com.backendProject.shoppingApp.exception.UserNotFoundException;
import com.backendProject.shoppingApp.model.User;
import com.backendProject.shoppingApp.repository.user.UserRepository;
import com.backendProject.shoppingApp.utils.Messages;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	

	@Override
	public User getUserById(Long userId) {

		return userRepository.findById(userId).orElseThrow(() -> 
		new UserNotFoundException(Messages.USER_NOTFOUND));
	}

	@Override
	public User createUser(RequestUserCreateDto request) {
		
		return Optional.of(request)
				.filter(user -> !userRepository.existsByEmail(request.getEmail()))
				.map(req -> {
					User user = User.builder()
									.email(req.getEmail())
									.password(passwordEncoder.encode(req.getPassword()))
									.firstName(req.getFirstName())
									.lastName(req.getLastName())
									.build();
					return userRepository.save(user);
				}).orElseThrow(() -> new RuntimeException(request.getEmail() + " already exist "));
	}

	@Override
	public User updateUser(RequestUserUpateDto request, Long userId) {
		return userRepository.findById(userId)
				.map(existingUser -> {
					existingUser.setFirstName(request.getFirstName());
					existingUser.setLastName(request.getLastName());
					return userRepository.save(existingUser);
					

					
		}).orElseThrow(()-> new UserNotFoundException(Messages.USER_NOTFOUND));
	
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository:: delete,
				() ->{
					throw new UserNotFoundException(Messages.USER_NOTFOUND);
				});		
	}
	
	@Override
	public  UserDto convertToDto(User user) {
		 return modelMapper.map(user,UserDto.class);
	}

	@Override
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.isAuthenticated()) {
			String email =   authentication.getName();
			return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(Messages.USER_NOTFOUND));
		}
		return null;
		
		
	}

}
