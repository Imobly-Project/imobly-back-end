package com.imobly.imobly.controllers.tenant.dtos

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SelfUpdateTenantDTO(

    @field:NotNull(message = "O campo E-mail é obrigatório")
    @field:Email(message = "O E-mail informado é inválido")
    @field:Size(min = 3, max = 100, message = "O campo E-mail deve ter entre 3  e 100 caracteres")
    val email: String? = "",

    @field:Valid
    @field:NotNull(message = "O campo telefone é obrigatório")
    val telephones: TelephoneDTO? = TelephoneDTO(),
)