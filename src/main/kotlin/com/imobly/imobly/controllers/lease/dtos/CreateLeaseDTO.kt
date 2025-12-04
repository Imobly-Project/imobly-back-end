package com.imobly.imobly.controllers.lease.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateLeaseDTO(
    @field:NotNull(message = "O campo data de início é obrigatório")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val startDate: LocalDate?,

    @field:NotNull(message = "O campo data de término é obrigatório")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val endDate: LocalDate?,

    @field:NotNull(message = "O campo ID da propriedade é obrigatório")
    val propertyId: String?,

    @field:NotNull(message = "O campo ID do locatário é obrigatório")
    val tenantId: String?,

    @field:NotNull(message = "O campo aluguel é obrigatório")
    @field:Min(0, message = "O valor mínimo do aluguel é 0,00")
    @field:Max(1_000_000, message = "O valor máximo do aluguel é 1 000 000,00")
    val monthlyRent: Double?,

    @field:Min(0, message = "O valor mínimo do depósito é 0,00")
    @field:Max(1_000_000, message = "O valor máximo do depósito é 1.000.000,00")
    val securityDeposit: Double?,

    @field:NotNull(message = "O campo data de vencimento é obrigatório")
    @field:Min(1, message = "O menor valor possível para a data de vencimento é 1")
    @field:Max(31, message = "O maior valor possível para a data de vencimento é 31")
    val paymentDueDay: Int?
)