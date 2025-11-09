package com.imobly.imobly.controllers.tenant.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class TelephoneDTO(

    @field:NotNull(message = "Ao menos 1 telefone é obrigatório")
    @field:Pattern(
        regexp = "^\\(\\d{2}\\)( )?(\\d{4}|\\d{5})-\\d{4}$",
        message = "Este telefone está inválido"
    )
    val telephone1: String? = "",

    @field:Pattern(
        regexp = "(^\\(\\d{2}\\)( )?(\\d{4}|\\d{5})-\\d{4}$|^$)",
        message = "Este telefone está inválido"
    )
    val telephone2: String? = "",

    @field:Pattern(
        regexp = "(^\\(\\d{2}\\)( )?(\\d{4}|\\d{5})-\\d{4}$|^$)",
        message = "Este telefone está inválido"
    )
    val telephone3: String? = ""
)