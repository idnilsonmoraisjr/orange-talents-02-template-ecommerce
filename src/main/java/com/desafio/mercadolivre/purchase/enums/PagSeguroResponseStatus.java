package com.desafio.mercadolivre.purchase.enums;

public enum PagSeguroResponseStatus {
	SUCCESS, FAIL;

	public PaymentStatus verify() {
		if(this.equals(SUCCESS)) {
			return PaymentStatus.success;
		}
		
		return PaymentStatus.fail;
	}
}
