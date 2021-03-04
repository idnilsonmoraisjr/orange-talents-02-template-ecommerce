package com.desafio.mercadolivre.shared.security;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.mercadolivre.user.User;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private EntityManager entityManager;
	
	public AuthenticationByTokenFilter(TokenService tokenService, EntityManager entityManager) {
		this.tokenService = tokenService;
		this.entityManager = entityManager;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = retrieveToken(request);
		boolean valido = tokenService.isValidToken(token);
		if(valido) {
			authenticateUser(token);
		}
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token) {
		
		Long userId = tokenService.getUserId(token);
		
		Query query = entityManager.createQuery("select u from User u where u.id = :id");
		query.setParameter("id", userId);
		User user = (User) query.getSingleResult(); 
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		// Considera o tamanho da palavra Bearer + " "
		return token.substring(7, token.length());
	}
}
