package com.desafio.mercadolivre.shared.security;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	@Value("${security.username-query}")
	private String query;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Optional<Object> user = Optional.ofNullable(manager.createQuery(query)
				.setParameter("login", login).getSingleResult());
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Unable to find user with login: "
					+ login);
		}
		return (UserDetails) user.get();
	}

}
