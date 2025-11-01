package com.imobly.imobly.controllers.payment.dtos

import com.imobly.imobly.domains.enums.PaymentStatusEnum

data class StatusInstallmentDTO(
    val status: PaymentStatusEnum?
)