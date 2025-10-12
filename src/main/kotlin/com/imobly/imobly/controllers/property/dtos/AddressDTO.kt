package com.imobly.imobly.controllers.property.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AddressDTO (
    @field:NotNull(message = "O campo CEP é obrigatório")
    @field:Size(min = 8, max = 9, message = "O campo CEP deve estar entre 8  e 9")
    val cep: String?,
    @field:NotNull(message = "O campo estado é obrigatório")
    @field:Size(min = 2, max = 20, message = "O campo estado deve ter entre 2  e 20 caracteres")
    val state: String?,
    @field:NotNull(message = "O campo cidade é obrigatório")
    @field:Size(min = 1, max = 50, message = "O campo cidade deve ter entre 1  e 50 caracteres")
    val city: String?,
    @field:NotNull(message = "O campo bairro é obrigatório")
    @field:Size(min = 1, max = 60, message = "O campo bairro deve ter entre 1  e 60 caracteres")
    val neighborhood: String?,
    @field:NotNull(message = "O campo rua é obrigatório")
    @field:Size(min = 1, max = 60, message = "O campo rua deve ter entre 1  e 60 caracteres")
    val street: String?,
    @field:NotNull(message = "O campo número é obrigatório")
    @field:Size(min = 1, max = 5, message = "O campo número deve estar entre 1  e 99 999")
    val number: String?,
    @field:Size(min = 0, max = 20, message = "O campo complemento deve ter entre 1  e 20 caracteres")
    val complement: String?
) {
    constructor(): this(null, null, null, null, null, null, null)
}