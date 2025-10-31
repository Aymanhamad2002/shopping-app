package com.backendProject.shoppingApp.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backendProject.shoppingApp.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopUserDetails implements UserDetails{
	private Long id ;
	private String email;
	private String password;
	private Collection<GrantedAuthority> authorities;
	
	public static ShopUserDetails buildUserDetails(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role ->{
			return new SimpleGrantedAuthority(role.getName());
		}).collect(Collectors.toList());
		
		return ShopUserDetails.builder()
				.id(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.authorities(authorities)
				.build();
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
