package com.backendProject.shoppingApp.data;

import java.util.List;
import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backendProject.shoppingApp.model.Role;
import com.backendProject.shoppingApp.model.User;
import com.backendProject.shoppingApp.repository.role.RoleRepository;
import com.backendProject.shoppingApp.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Set<String> defaultRoles = Set.of("ROLE_ADMIN","ROLE_USER");
		createDefaultRoleIfNotExits(defaultRoles);
		createDefaultUserIfNotExists();
		createDefaultAdminIfNotExists();
		
		
	}

	private void createDefaultUserIfNotExists() {
		
			for(int i = 1  ; i <= 5 ;i ++) {
				String defaultEmail = "user"+i+"@gmail.com";
				if(userRepository.existsByEmail(defaultEmail)) {
					continue;
				}
				User user = User.builder()
						.firstName("The User")
						.lastName("User" + i)
						.email(defaultEmail)
						.password(passwordEncoder.encode("123456"))
						.roles(Set.of(roleRepository.findByName("ROLE_USER").get(0)))
						.build();
				userRepository.save(user);
				
			}
		
	}
	private void createDefaultAdminIfNotExists() {
		
		for(int i = 1  ; i <= 3 ;i ++) {
			String defaultEmail = "admin"+i+"@gmail.com";
			if(userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			User admin = User.builder()
					.firstName("The admin")
					.lastName("Admin" + i)
					.email(defaultEmail)
					.password(passwordEncoder.encode("123456"))
					.roles(Set.of(roleRepository.findByName("ROLE_ADMIN").get(0)))
					.build();
			userRepository.save(admin);
			
		}
	
}

	
	private void createDefaultRoleIfNotExits(Set<String> roles) {
		roles.stream()
		.filter(role ->roleRepository.findByName(role).isEmpty())
		.map(Role:: new).forEach(roleRepository::save);
		
	}

}
