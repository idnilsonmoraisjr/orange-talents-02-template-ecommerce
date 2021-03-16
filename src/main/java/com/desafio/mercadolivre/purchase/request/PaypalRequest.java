package com.desafio.mercadolivre.purchase.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.desafio.mercadolivre.purchase.Payment;
import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.purchase.enums.PaymentStatus;
import com.desafio.mercadolivre.purchase.response.PaymentMethodResponse;

public class PaypalRequest implements PaymentMethodResponse {

	@NotBlank
	private String transactionId;
	@NotNull
	@Min(0)
	@Max(1)
	private int status;
	
	public PaypalRequest(@NotBlank String transactionId, @NotNull @Min(0) @Max(1) int status) {
		this.transactionId = transactionId;
		this.status = status;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public int getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return "PagSeguroRequest [transactionId=" + transactionId + ", status=" + status + "]";
	}

	public Payment toPayment(Purchase purchase) {
		@NotNull
		PaymentStatus paymentStatus = this.status == 0? PaymentStatus.fail : PaymentStatus.success;
		return new Payment(this.transactionId, paymentStatus, purchase);
	}
}
