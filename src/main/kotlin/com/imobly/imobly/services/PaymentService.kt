package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import com.imobly.imobly.domains.leases.LeaseDomain
import com.imobly.imobly.domains.payments.MonthlyInstallmentDomain
import com.imobly.imobly.domains.payments.PaymentDomain
import com.imobly.imobly.domains.payments.StatusInstallmentDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.payment.mappers.PaymentPersistenceMapper
import com.imobly.imobly.persistences.payment.repositories.PaymentRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository, private val mapper: PaymentPersistenceMapper) {
    fun findAll(): List<PaymentDomain> = mapper.toDomains(paymentRepository.findAll())

    fun findById(id: String): PaymentDomain =
        mapper.toDomain(paymentRepository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0017)
        }))

    fun insert(lease: LeaseDomain): PaymentDomain {
        val numberInstallment = ChronoUnit.MONTHS.between(lease.startDate, lease.endDate)
        val date = LocalDate.of(lease.startDate.year, lease.startDate.month, lease.paymentDueDay)
        val installments = mutableListOf<MonthlyInstallmentDomain>()
        installments.add(MonthlyInstallmentDomain(
            monthlyRent = lease.monthlyRent,
            status = PaymentStatusEnum.PENDING,
            dueDate = lease.startDate,
            month = lease.startDate.month
        ))
        for (nInstallment in 1 .. numberInstallment) {
            installments.add(MonthlyInstallmentDomain(
                monthlyRent = lease.monthlyRent,
                status = PaymentStatusEnum.PENDING,
                dueDate = date.plusMonths(nInstallment),
                month = lease.startDate.month
            ))
        }
        val payment = PaymentDomain(lease = lease, installments = installments)
        val paymentSaved = paymentRepository.save(mapper.toEntity(payment))
        return mapper.toDomain(paymentSaved)
    }

    fun updateStatusInstallment(idPayment: String, idInstallment: String, statusInstallment: StatusInstallmentDomain) {
        val payment = mapper.toDomain(paymentRepository.findById(idPayment).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0017)
        }))
        payment.installments = payment.installments.map {
            if (it.id == idInstallment) it.status = statusInstallment.status
            it
        }
        paymentRepository.save(mapper.toEntity(payment))
    }
}