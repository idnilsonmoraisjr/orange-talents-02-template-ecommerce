package com.desafio.mercadolivre.shared;

import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.desafio.mercadolivre.product.NewProductPostRequest;

public class UniqueProductAttributeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return NewProductPostRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(errors.hasErrors()) {
			return;
		}
		NewProductPostRequest request = (NewProductPostRequest) target;
		Set<String> sameName = request.findEqualAttributes();
		if(!sameName.isEmpty()) {
			errors.rejectValue("productAttributes", null, "It is not possible to register a product with repeated attributes" + sameName);
		}
	}

}
