package com.imobly.imobly.persistences.payment.mappers

import com.imobly.imobly.domains.payments.MonthlyInstallmentDomain
import com.imobly.imobly.persistences.payment.entities.MonthlyInstallmentEntity
import org.springframework.stereotype.Component

@Component
class MonthlyInstallmentPersistenceMapper {
    fun toDomain(monthlyInstallment: MonthlyInstallmentEntity): MonthlyInstallmentDomain =
        MonthlyInstallmentDomain(
            id = monthlyInstallment.id,
            monthlyRent = monthlyInstallment.monthlyRent,
            status = monthlyInstallment.status,
            dueDate = monthlyInstallment.dueDate,
            month = monthlyInstallment.month
        )

    fun toEntity(monthlyInstallment: MonthlyInstallmentDomain): MonthlyInstallmentEntity =
        MonthlyInstallmentEntity(
            id = monthlyInstallment.id,
            monthlyRent = monthlyInstallment.monthlyRent,
            status = monthlyInstallment.status,
            dueDate = monthlyInstallment.dueDate,
            month = monthlyInstallment.month
        )

    fun toDomains(monthlyInstallments: List<MonthlyInstallmentEntity>): List<MonthlyInstallmentDomain> =
        monthlyInstallments.map{
            toDomain(it)
        }

    fun toEntities(monthlyInstallments: List<MonthlyInstallmentDomain>): List<MonthlyInstallmentEntity> =
        monthlyInstallments.map{
            toEntity(it)
        }
}