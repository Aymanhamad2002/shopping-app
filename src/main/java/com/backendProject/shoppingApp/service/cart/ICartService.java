package com.backendProject.shoppingApp.service.cart;

import java.math.BigDecimal;

import com.backendProject.shoppingApp.dto.CartDto;
import com.backendProject.shoppingApp.model.Cart;
import com.backendProject.shoppingApp.model.User;

public interface ICartService {
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Cart initializeNewCart(User user);
	Cart getCartByUserId(Long userId);
	CartDto convertToDto(Cart cart);
	

}
