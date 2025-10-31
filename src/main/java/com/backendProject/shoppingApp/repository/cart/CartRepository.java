package com.backendProject.shoppingApp.repository.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Optional<Cart> findCartByUserId(Long userId);

}
