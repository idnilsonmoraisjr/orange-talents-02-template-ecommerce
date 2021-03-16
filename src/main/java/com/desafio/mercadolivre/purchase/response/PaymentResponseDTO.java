package com.desafio.mercadolivre.purchase.response;

import java.util.Set;

import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.purchase.enums.PaymentStatus;

public class PaymentResponseDTO {

	private Set<PaymentStatus> paymentStatus;

	public PaymentResponseDTO(Purchase purchase) {
		this.paymentStatus = purchase.getPaymentStatus();
	}

	public Set<PaymentStatus> getPaymentStatus() {
		return paymentStatus;
	}
}
