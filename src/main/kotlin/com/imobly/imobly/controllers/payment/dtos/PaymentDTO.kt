package com.imobly.imobly.controllers.payment.dtos

import com.imobly.imobly.controllers.lease.dtos.LeaseDTO

data class PaymentDTO(
    val id: String? = null,
    val lease: LeaseDTO? = LeaseDTO(),
    val installments: List<MonthlyInstallmentDTO>? = emptyList()
)