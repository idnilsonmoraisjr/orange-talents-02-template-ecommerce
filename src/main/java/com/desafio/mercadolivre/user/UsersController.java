package com.desafio.mercadolivre.user;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

	private EntityManager entityManager;
	
	UsersController(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastra(@RequestBody @Valid NewUserPostRequest request) {
		User newUser = request.toModel();
		entityManager.persist(newUser);
		return ResponseEntity.ok().build();
	}
}
