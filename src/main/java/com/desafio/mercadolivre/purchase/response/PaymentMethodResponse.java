package com.desafio.mercadolivre.purchase.response;

import com.desafio.mercadolivre.purchase.Payment;
import com.desafio.mercadolivre.purchase.Purchase;

public interface PaymentMethodResponse {

	/**
	 * @param Purchase
	 * @return A new payment method
	 */
	Payment toPayment(Purchase purchase);
}
