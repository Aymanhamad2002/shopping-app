package com.backendProject.shoppingApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendProject.shoppingApp.exception.CartNotFoundException;
import com.backendProject.shoppingApp.model.Cart;
import com.backendProject.shoppingApp.model.User;
import com.backendProject.shoppingApp.response.ApiResponse;
import com.backendProject.shoppingApp.service.cart.ICartService;
import com.backendProject.shoppingApp.service.cartItem.ICartItemService;
import com.backendProject.shoppingApp.service.user.IUserService;
import com.backendProject.shoppingApp.service.user.UserService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
	private final ICartItemService cartItemService;
	private final ICartService cartService;
	private final IUserService userService;
	
	
	
	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required= false) Long userId,
													@RequestParam Long productId,
													@RequestParam Integer quantity){
		try {
				User user =  userService.getAuthenticatedUser();
				Cart cart = cartService.initializeNewCart(user);
			cartItemService.addItemToCart(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("add item successfuly", null));
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch(JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
	@DeleteMapping("/item/delete/{cartId}/{productId}")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable  Long cartId, @PathVariable Long productId){
		try {
			cartItemService.removeitemFromCart(cartId, productId);
			return ResponseEntity.ok(new ApiResponse("item removed successfuly ", null));
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}

	@PutMapping("/item/update/{cartId}/{productId}")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable	Long cartId
														, @PathVariable Long productId
														,@RequestParam Integer quantity){
		try {
			cartItemService.updateItemQuantity(cartId, productId, quantity);;
			return ResponseEntity.ok(new ApiResponse("quantity updated successfuly successfuly ", null));
		} catch (CartNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
		
	}

}
