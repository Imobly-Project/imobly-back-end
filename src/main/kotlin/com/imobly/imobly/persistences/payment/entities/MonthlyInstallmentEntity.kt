package com.imobly.imobly.persistences.payment.entities

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.Month

@Entity
@Table(name = "tb_parcela")
class MonthlyInstallmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    val monthlyRent: Double,
    val status: PaymentStatusEnum,
    val dueDate: LocalDate,
    val month: Month
)