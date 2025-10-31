package com.backendProject.shoppingApp.service.cartItem;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.backendProject.shoppingApp.exception.ProductNotFoundException;
import com.backendProject.shoppingApp.model.Cart;
import com.backendProject.shoppingApp.model.CartItem;
import com.backendProject.shoppingApp.model.Product;
import com.backendProject.shoppingApp.repository.cart.CartRepository;
import com.backendProject.shoppingApp.repository.cartItem.CartItemRepository;
import com.backendProject.shoppingApp.service.cart.ICartService;
import com.backendProject.shoppingApp.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final IProductService productService;
	private final ICartService cartService;


	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		//1. get the cart ;
		//2. get the product 
		//3.get check if the product already in the cart
		//4 if Yes , then increase quantity  with the requested quantity 
		//5 if no,  then initiate a new CartItem entry 
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		CartItem cartItem = cart.getCartItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(new CartItem());
		if(cartItem.getId() == null) {
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);
			cartItem.setUnitPrice(product.getPrice());
		}else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
				
		
		
	}

	@Override
	public void removeitemFromCart(Long cartId, Long productId) {
		
		Cart cart = cartService.getCart(cartId);
		CartItem  itemToRemove = getCartItem(cartId, productId);
				
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
				
		
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
	
				cart.getCartItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.ifPresent(item ->{
					item.setQuantity(quantity);
					item.setTotalPrice();
					
				});
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
		
			
		
		
	}
	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		CartItem  itemToRemove = cart
				.getCartItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow( () -> new ProductNotFoundException("Product not found "));
		return itemToRemove;
	}
	
	

}
