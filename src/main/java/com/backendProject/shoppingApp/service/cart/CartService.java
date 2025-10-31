	package com.backendProject.shoppingApp.service.cart;
	
	import java.lang.StackWalker.Option;
	import java.math.BigDecimal;
	import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backendProject.shoppingApp.dto.CartDto;
import com.backendProject.shoppingApp.exception.CartNotFoundException;
	import com.backendProject.shoppingApp.model.Cart;
	import com.backendProject.shoppingApp.model.CartItem;
	import com.backendProject.shoppingApp.model.User;
	import com.backendProject.shoppingApp.repository.cart.CartRepository;
	import com.backendProject.shoppingApp.repository.cartItem.CartItemRepository;
	import com.backendProject.shoppingApp.repository.product.ProductRepository;
import com.backendProject.shoppingApp.repository.user.UserRepository;
import com.backendProject.shoppingApp.utils.Messages;
	
	import jakarta.transaction.Transactional;
	import lombok.RequiredArgsConstructor;

@Service
	@RequiredArgsConstructor
	public class CartService implements ICartService {

        
		private final CartRepository cartRepository;
		private final CartItemRepository cartItemRepository;
		private final ModelMapper modelMapper ;

		
	
	
		
		
	
		@Override
		public Cart getCart(Long id) {
		    Cart cart = cartRepository.findById(id)
		        .orElseThrow(() -> new CartNotFoundException(Messages.CART_NOTFOUND));
		    cart.updateTotalAmount();
		    return cart;
		}
		
		
		@Transactional
		@Override
		public void clearCart(Long id) {
			Cart cart = getCart(id);
			cartItemRepository.deleteAllByCartId(id);
			cart.getCartItems().clear();
			cart.setTotalAmount(BigDecimal.ZERO);
			cartRepository.save(cart);
			
		}
	
		@Override
		public BigDecimal getTotalPrice(Long id) {
			// TODO Auto-generated method stub
			Cart cart = getCart(id);
			return cart.getTotalAmount();
		}
		@Override
		public Cart initializeNewCart(User user) {
			return cartRepository.findCartByUserId(user.getId())
					.orElseGet(()-> {
						Cart cart = new Cart();
						cart.setUser(user);
						return cartRepository.save(cart);
						
					});
			
		}
	
	
		@Override
		public Cart getCartByUserId(Long userId) {
			return cartRepository
					.findCartByUserId(userId)
					.orElseThrow(() -> new CartNotFoundException(Messages.CART_NOTFOUND));
			
		}
		@Override
		public CartDto convertToDto(Cart cart) {
			return modelMapper.map(cart, CartDto.class);
		}
	
	}
