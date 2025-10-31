package com.backendProject.shoppingApp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendProject.shoppingApp.dto.LoginRequestDto;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.response.JwtResponse;
import com.backendProject.shoppingApp.security.jwt.JwtUtils;
import com.backendProject.shoppingApp.security.user.ShopUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login( @RequestBody LoginRequestDto request){
		try {
			Authentication authentication = authenticationManager
					.authenticate((new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = jwtUtils.generateTokenForUser(authentication);
			ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
			JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwtToken);

			return ResponseEntity.ok(new ApiResponse("login sucess", jwtResponse));
		}catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(" Invalid email or passowrd", null));
		}
						
		}
		
		
	

}
