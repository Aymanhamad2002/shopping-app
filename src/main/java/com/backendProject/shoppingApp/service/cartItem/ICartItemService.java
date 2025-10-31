package com.backendProject.shoppingApp.service.cartItem;

import com.backendProject.shoppingApp.model.CartItem;

public interface ICartItemService {
	void addItemToCart(Long cartId , Long productId , int quantity);
	void removeitemFromCart(Long cartId , Long productId);
	void updateItemQuantity(Long cartId, Long productId, int quantity);
	CartItem getCartItem(Long cartId, Long productId);
	

}
