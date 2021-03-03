package com.desafio.mercadolivre.shared.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.desafio.mercadolivre.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${ecommerce.jwt.expiration}")
	private String expiration;

	@Value("${ecommerce.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		User loggedIn = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API Forum")
				.setSubject(loggedIn.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
}
