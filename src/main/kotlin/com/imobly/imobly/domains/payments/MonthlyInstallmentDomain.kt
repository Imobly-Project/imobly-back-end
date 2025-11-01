package com.imobly.imobly.domains.payments

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import java.time.LocalDate

class MonthlyInstallmentDomain(
    var id: String? = null,
    var monthlyRent: Double = 0.0,
    var status: PaymentStatusEnum = PaymentStatusEnum.PENDING,
    var dueDate: LocalDate = LocalDate.of(2000, 1, 1)
)