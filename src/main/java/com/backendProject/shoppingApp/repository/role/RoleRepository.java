package com.backendProject.shoppingApp.repository.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendProject.shoppingApp.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long >{
	List<Role> findByName(String name);

}
