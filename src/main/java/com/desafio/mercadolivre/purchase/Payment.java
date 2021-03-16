package com.desafio.mercadolivre.purchase;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.desafio.mercadolivre.purchase.enums.PaymentStatus;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String paymentTransactionId;
	@NotNull 
	private PaymentStatus status;
	@NotNull
	@Column(name = "processing_moment", updatable = false)
	private LocalDateTime processingMoment = LocalDateTime.now();
	@NotNull @Valid @ManyToOne
	private Purchase purchase;
	
	@Deprecated
	public Payment() {}
	
	public Payment(@NotBlank String transactionId, @NotNull PaymentStatus status, @NotNull @Valid Purchase purchase) {
		this.paymentTransactionId = transactionId;
		this.status = status;
		this.purchase = purchase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paymentTransactionId == null) ? 0 : paymentTransactionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (paymentTransactionId == null) {
			if (other.paymentTransactionId != null)
				return false;
		} else if (!paymentTransactionId.equals(other.paymentTransactionId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentTransactionId=" + paymentTransactionId + ", status=" + status
				+ ", processingMoment=" + processingMoment + "]";
	}

	public boolean successfullyPayment() {
		return this.status.equals(PaymentStatus.success);
	}

	public PaymentStatus getStatus() {
		return status;
	}
}
