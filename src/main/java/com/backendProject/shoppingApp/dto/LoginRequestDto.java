package com.backendProject.shoppingApp.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequestDto {
	
	private String email;
	private String password;
	

}
