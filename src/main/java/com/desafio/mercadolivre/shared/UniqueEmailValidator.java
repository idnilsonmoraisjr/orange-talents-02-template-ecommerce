package com.desafio.mercadolivre.shared;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void initialize(UniqueEmail constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		TypedQuery<Long> query = manager.createQuery("select count(u) from User u where u.email =:email", Long.class);
		query.setParameter("email", email);

		Long count = query.getSingleResult();
		return count == 0;
	}

	
	
}
