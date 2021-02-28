package com.desafio.mercadolivre.shared;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutputDto {

	List<String> globalErrorMessages = new ArrayList<>();
	private List<FieldErrorOutputDto> fieldErrors = new ArrayList<>();
	
	public void addError(String message) {
		globalErrorMessages.add(message);
	}
	
	public void addFieldError(String field, String message) {
		FieldErrorOutputDto fieldError = new FieldErrorOutputDto(field, message);
		fieldErrors.add(fieldError);
	}
	
	public List<String> getGlobalErrorsMessages() {
		return globalErrorMessages;
	}
	
	public List<FieldErrorOutputDto> getErrors() {
		return fieldErrors;
	}
	
	public int getNumberOfErrors() {
		return this.globalErrorMessages.size() + this.fieldErrors.size();
	}
}
