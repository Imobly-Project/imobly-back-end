package com.imobly.imobly.controllers.tenant.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TenantDTO(
    val id: String?,

    @field:NotNull(message = "O campo nome é obrigatório")
    @field:Size(min = 3, max = 50, message = "O campo nome deve ter entre 3  e 50 caracteres")
    val name: String,

    @field:NotNull(message = "O campo email é obrigatório")
    @field:Email(message = "O e-mail informado é inválido")
    val email: String,

    @field:NotNull(message = "O campo senha é obrigatório")
    @field:Size(min = 6, max = 50, message = "O campo senha deve ter entre 6  e 50 caracteres")
    val password: String,

    @field:NotNull(message = "O campo RG é obrigatório")
    @field:Size(min = 7, max = 9, message = "O campo RG deve ter entre 7  e 9 caracteres")
    val rg: String,

    @field:NotNull(message = "O campo CPF é obrigatório")
    @field:Size(min = 11, max = 11, message = "O campo CPF deve ter 11 caracteres")
    val cpf: String,

    @field:NotNull(message = "O campo data de nascimento é obrigatório")
    val birthDate: String,

    @field:NotNull(message = "O campo nacionalidade é obrigatório")
    @field:Size(min = 3, max = 50, message = "O campo nacionalidade deve ter entre 3  e 50 caracteres")
    val nationality: String,

    @field:NotNull(message = "O campo estado civil é obrigatório")
    @field:Size(min = 3, max = 20, message = "O campo estado civil deve ter entre 3  e 20 caracteres")
    val maritalStatus: String
)