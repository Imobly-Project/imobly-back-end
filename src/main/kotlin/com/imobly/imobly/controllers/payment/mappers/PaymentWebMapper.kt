package com.imobly.imobly.controllers.payment.mappers

import com.imobly.imobly.controllers.lease.dtos.LeaseDTO
import com.imobly.imobly.controllers.lease.mappers.LeaseWebMapper
import com.imobly.imobly.controllers.payment.dtos.PaymentDTO
import com.imobly.imobly.domains.payments.PaymentDomain
import org.springframework.stereotype.Component

@Component
class PaymentWebMapper(
    val leaseMapper: LeaseWebMapper,
    val monthlyInstallmentMapper: MonthlyInstallmentWebMapper,
) {
    fun toDomain(payment: PaymentDTO): PaymentDomain =
        PaymentDomain(
            id = payment.id,
            lease = leaseMapper.toDomain(payment.lease ?: LeaseDTO()),
            installments = monthlyInstallmentMapper.toDomains(payment.installments ?: emptyList())
        )

    fun toDTO(payment: PaymentDomain): PaymentDTO =
        PaymentDTO(
            id = payment.id,
            lease = leaseMapper.toDTO(payment.lease),
            installments = monthlyInstallmentMapper.toDTOs(payment.installments),
        )

    fun toDomains(payments: List<PaymentDTO>): List<PaymentDomain> =
        payments.map{ toDomain(it) }

    fun toDTOs(payments: List<PaymentDomain>): List<PaymentDTO> =
        payments.map{ toDTO(it) }
}