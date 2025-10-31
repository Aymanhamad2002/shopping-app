package com.backendProject.shoppingApp.repository.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

	void deleteAllByCartId(Long id);
	

}
