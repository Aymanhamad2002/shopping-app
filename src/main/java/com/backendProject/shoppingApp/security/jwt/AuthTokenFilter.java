package com.backendProject.shoppingApp.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backendProject.shoppingApp.security.user.ShopUserDetailsService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class AuthTokenFilter extends OncePerRequestFilter {
	private JwtUtils jwtUtils;
	private ShopUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String jwt = parseJwt(request);
			if(StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
				String email = jwtUtils.getUsernameFromToke(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				Authentication auth = new UsernamePasswordAuthenticationToken(
				        userDetails,
				        null, // no credentials
				        userDetails.getAuthorities() // roles/authorities go here
				);

				SecurityContextHolder.getContext().setAuthentication(auth);
			} 
			
			
		}
		catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(e.getMessage() + ": Invalid or expired token , try to login first");
			return ; 
			
		}
		filterChain.doFilter(request, response);
		
		
	}
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7);
		}
		return null;
	}

}
