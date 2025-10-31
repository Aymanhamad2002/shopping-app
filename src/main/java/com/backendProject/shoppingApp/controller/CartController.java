package com.backendProject.shoppingApp.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendProject.shoppingApp.dto.CartDto;
import com.backendProject.shoppingApp.exception.CartNotFoundException;
import com.backendProject.shoppingApp.model.Cart;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.service.cart.ICartService;
import com.backendProject.shoppingApp.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
	private final ICartService cartService;
	
	
	@GetMapping("/cart/id/{cartId}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
		try {
			Cart cart = cartService.getCart(cartId);
			CartDto cartDto = cartService.convertToDto(cart);
			

			return ResponseEntity.ok(new ApiResponse("returned successefuly", cartDto));
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("cart not found ", null));
		}		
		
		
	}
	@DeleteMapping("/cart/clear/{cartId}")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
		try {
			cartService.clearCart(cartId);
			return ResponseEntity.ok(new ApiResponse("cleared successfully", null));
		}catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("cart not found ", null));
		}
		
	}
	@GetMapping("/cart/amount/{cartId}")
	public ResponseEntity<ApiResponse> getTotalamount(@PathVariable Long cartId){
		try {
			BigDecimal totalPrice = cartService.getTotalPrice(cartId);
			return ResponseEntity.ok(new ApiResponse("returned successfully", totalPrice));
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("cart not found ", null));
		
	}

}
}
