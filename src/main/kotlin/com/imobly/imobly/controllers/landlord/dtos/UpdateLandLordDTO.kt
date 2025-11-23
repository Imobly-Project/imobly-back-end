package com.imobly.imobly.controllers.landlord.dtos

import com.imobly.imobly.controllers.tenant.dtos.TelephoneDTO
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateLandLordDTO (
    @field:NotNull(message = "O campo nome é obrigatório")
    @field:Size(min = 3, max = 40, message = "O campo nome deve ter entre 3  e 50 caracteres")
    val firstName: String?,

    @field:NotNull(message = "O campo sobrenome é obrigatório")
    @field:Size(min = 3, max = 40, message = "O campo sobrenome deve ter entre 3  e 50 caracteres")
    val lastName: String?,

    @field:Valid
    @field:NotNull(message = "O campo telefone é obrigatório")
    val telephones: TelephoneDTO?
)