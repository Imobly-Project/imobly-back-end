package com.imobly.imobly.controllers.payment.mappers

import com.imobly.imobly.controllers.payment.dtos.MonthlyInstallmentDTO
import com.imobly.imobly.domains.enums.PaymentStatusEnum
import com.imobly.imobly.domains.payments.MonthlyInstallmentDomain
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class MonthlyInstallmentWebMapper {
    fun toDomain(monthlyInstallment: MonthlyInstallmentDTO): MonthlyInstallmentDomain =
        MonthlyInstallmentDomain(
            id = monthlyInstallment.id,
            monthlyRent = monthlyInstallment.monthlyRent ?: 0.0,
            status = monthlyInstallment.status ?: PaymentStatusEnum.PENDING,
            dueDate = monthlyInstallment.dueDate ?: LocalDate.of(2000, 1, 1)
        )

    fun toDTO(monthlyInstallment: MonthlyInstallmentDomain): MonthlyInstallmentDTO =
        MonthlyInstallmentDTO(
            id = monthlyInstallment.id,
            monthlyRent = monthlyInstallment.monthlyRent,
            status = monthlyInstallment.status,
            dueDate = monthlyInstallment.dueDate
        )

    fun toDomains(monthlyInstallments: List<MonthlyInstallmentDTO>): List<MonthlyInstallmentDomain> =
        monthlyInstallments.map{
            toDomain(it)
        }

    fun toDTOs(monthlyInstallments: List<MonthlyInstallmentDomain>): List<MonthlyInstallmentDTO> =
        monthlyInstallments.map{
            toDTO(it)
        }
}