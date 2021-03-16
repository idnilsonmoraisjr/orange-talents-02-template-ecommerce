package com.desafio.mercadolivre.purchase.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.desafio.mercadolivre.purchase.Payment;
import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.purchase.enums.PagSeguroResponseStatus;
import com.desafio.mercadolivre.purchase.response.PaymentMethodResponse;

public class PagSeguroRequest implements PaymentMethodResponse {

	@NotBlank
	private String transactionId;
	@NotNull
	private PagSeguroResponseStatus status;
	
	public PagSeguroRequest(@NotBlank String transactionId, PagSeguroResponseStatus status) {
		this.transactionId = transactionId;
		this.status = status;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public PagSeguroResponseStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "PagSeguroRequest [transactionId=" + transactionId + ", status=" + status + "]";
	}

	public Payment toPayment(Purchase purchase) {
		return new Payment( this.transactionId, this.status.verify(), purchase);
	}
}
