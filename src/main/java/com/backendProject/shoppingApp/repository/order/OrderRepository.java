package com.backendProject.shoppingApp.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userId);

}
