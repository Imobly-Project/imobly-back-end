package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import com.imobly.imobly.domains.LeaseDomain
import com.imobly.imobly.domains.payments.MonthlyInstallmentDomain
import com.imobly.imobly.domains.payments.PaymentDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.payment.mappers.PaymentPersistenceMapper
import com.imobly.imobly.persistences.payment.repositories.PaymentRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Year
import java.time.temporal.ChronoUnit

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository, private val mapper: PaymentPersistenceMapper) {

    fun findByLeaseId(id: String): PaymentDomain =
        mapper.toDomain(paymentRepository.findByLease_Id(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0017)
        })

    fun insert(lease: LeaseDomain): PaymentDomain {
        val installments = createInstallments(lease)
        val payment = PaymentDomain(lease = lease, installments = installments)
        val paymentSaved = paymentRepository.save(mapper.toEntity(payment))
        return mapper.toDomain(paymentSaved)
    }

    fun updateStatusInstallment(idPayment: String, idInstallment: String, statusInstallment: MonthlyInstallmentDomain) {
        val payment = mapper.toDomain(paymentRepository.findById(idPayment).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0017)
        })
        payment.installments = payment.installments.map {
            if (it.id == idInstallment) it.status = statusInstallment.status
            it
        }
        paymentRepository.save(mapper.toEntity(payment))
    }

    fun updatePaymentDueDateInstallment(lease: LeaseDomain) {
        val payment = mapper.toDomain(paymentRepository.findByLease_Id(lease.id ?: "").orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0017)
        })
        payment.installments = payment.installments.map {
            if (it.status == PaymentStatusEnum.PENDING) {
                val lastDay = it.dueDate.month.length(Year.isLeap(it.dueDate.year.toLong()))
                it.dueDate = it.dueDate.withDayOfMonth(minOf(lease.paymentDueDay, lastDay))
            }
            it
        }
        paymentRepository.save(mapper.toEntity(payment))
    }

    fun createInstallments(lease: LeaseDomain): MutableList<MonthlyInstallmentDomain> {
        val numberInstallment = ChronoUnit.MONTHS.between(lease.startDate, lease.endDate)
        val lastDay = lease.startDate.month.length(Year.isLeap(lease.startDate.year.toLong()))

        val date = LocalDate.of(
            lease.startDate.year,
            lease.startDate.monthValue,
            minOf(lease.paymentDueDay, lastDay)
        )

        val installments = mutableListOf<MonthlyInstallmentDomain>()
        for (nInstallment in 0 .. numberInstallment) {
            installments.add(MonthlyInstallmentDomain(
                monthlyRent = lease.monthlyRent,
                status = PaymentStatusEnum.PENDING,
                dueDate = date.plusMonths(nInstallment),
                month = date.plusMonths(nInstallment).month
            ))
        }
        return installments
    }
}