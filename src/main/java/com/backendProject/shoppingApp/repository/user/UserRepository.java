package com.backendProject.shoppingApp.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String username);

}
