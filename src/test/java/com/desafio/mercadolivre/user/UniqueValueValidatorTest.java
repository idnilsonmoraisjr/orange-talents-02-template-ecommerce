package com.desafio.mercadolivre.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.mercadolivre.shared.UniqueEmailValidator;

@ExtendWith(MockitoExtension.class)
public class UniqueValueValidatorTest {
	
	@Mock
	EntityManager entityManager;
	
	@InjectMocks
	UniqueEmailValidator validator;
	
	@Mock
	TypedQuery<Long> query;	
	
	@Before
	public void setup() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@DisplayName("Should return a valid login when it does not exist")
	public void shouldReturnValidLogin_WhenDoesNotExist() {
		when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(0L);
		
		assertTrue(validator.isValid("login@email.com", null));
	}
	
	@Test
	@DisplayName("Should return a invalid login when already exists")
	public void shouldReturnInvalidLogin_WhenAlreadyExists() {
		when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(1L);
		
		assertFalse(validator.isValid("login@email.com", null));
	}
	
	
}
