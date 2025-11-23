package com.imobly.imobly.controllers.passwordrecovery.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ResetPasswordDTO(
    @field:NotNull(message = "O campo E-mail é obrigatório")
    @field:Email(message = "O E-mail informado é inválido")
    @field:Size(min = 3, max = 100, message = "O campo E-mail deve ter entre 3  e 100 caracteres")
    val email: String,

    @field:NotNull(message = "O campo código é obrigatório")
    @field:Size(min = 6, max = 6, message = "O campo código deve ter entre 3  e 100 caracteres")
    val code:String,

    @field:NotNull(message = "O campo senha é obrigatório")
    @field:Size(min = 8, max = 50, message = "O campo senha deve ter entre 8  e 50 caracteres")
    val newPassword: String
)
