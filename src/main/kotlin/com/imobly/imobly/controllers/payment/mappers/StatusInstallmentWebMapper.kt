package com.imobly.imobly.controllers.payment.mappers

import com.imobly.imobly.controllers.payment.dtos.StatusInstallmentDTO
import com.imobly.imobly.domains.enums.PaymentStatusEnum
import com.imobly.imobly.domains.payments.StatusInstallmentDomain
import org.springframework.stereotype.Component

@Component
class StatusInstallmentWebMapper {
    fun toDomain(statusInstallment: StatusInstallmentDTO): StatusInstallmentDomain =
        StatusInstallmentDomain(
            status = statusInstallment.status ?: PaymentStatusEnum.PENDING,
        )

    fun toDTO(statusInstallment: StatusInstallmentDomain): StatusInstallmentDTO =
        StatusInstallmentDTO(
            status = statusInstallment.status,
        )
}