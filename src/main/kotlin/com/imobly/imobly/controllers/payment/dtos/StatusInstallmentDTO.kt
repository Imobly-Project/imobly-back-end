package com.imobly.imobly.controllers.payment.dtos

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import jakarta.validation.constraints.NotNull

data class StatusInstallmentDTO(
    @field:NotNull(message = "O campo status é obrigatório")
    val status: PaymentStatusEnum?
)