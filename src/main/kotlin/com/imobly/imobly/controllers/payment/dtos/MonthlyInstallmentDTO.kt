package com.imobly.imobly.controllers.payment.dtos

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import java.time.LocalDate

data class MonthlyInstallmentDTO(
    val id: String? = null,
    val monthlyRent: Double?,
    val status: PaymentStatusEnum?,
    val dueDate: LocalDate?
)