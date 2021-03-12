package com.desafio.mercadolivre.purchase;

public enum PaymentMethod {
	PAYPAL("paypal"), PAGSEGURO("pagseguro");

	private String value;
	
	PaymentMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value.toLowerCase();
	}

}
