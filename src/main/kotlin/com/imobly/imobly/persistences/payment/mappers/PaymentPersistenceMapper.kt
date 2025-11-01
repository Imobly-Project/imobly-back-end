package com.imobly.imobly.persistences.payment.mappers

import com.imobly.imobly.domains.payments.PaymentDomain
import com.imobly.imobly.persistences.lease.mappers.LeasePersistenceMapper
import com.imobly.imobly.persistences.payment.entities.PaymentEntity
import org.springframework.stereotype.Component

@Component
class PaymentPersistenceMapper(
    val leaseMapper: LeasePersistenceMapper,
    val monthlyInstallmentMapper: MonthlyInstallmentPersistenceMapper
) {
    fun toDomain(payment: PaymentEntity): PaymentDomain =
        PaymentDomain(
            id = payment.id,
            lease = leaseMapper.toDomain(payment.lease),
            installments = monthlyInstallmentMapper.toDomains(payment.installments)
        )

    fun toEntity(payment: PaymentDomain): PaymentEntity =
        PaymentEntity(
            id = payment.id,
            lease = leaseMapper.toEntity(payment.lease),
            installments = monthlyInstallmentMapper.toEntities(payment.installments)
        )

    fun toDomains(payments: List<PaymentEntity>): List<PaymentDomain> =
        payments.map { toDomain(it) }
}