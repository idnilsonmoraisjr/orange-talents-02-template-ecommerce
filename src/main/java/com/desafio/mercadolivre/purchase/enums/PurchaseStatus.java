package com.desafio.mercadolivre.purchase.enums;

public enum PurchaseStatus {
	STARTED("started");

	private String value;

	PurchaseStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value.toLowerCase();
	}
	
	
}
