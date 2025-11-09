package com.imobly.imobly.controllers.tenant.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.imobly.imobly.controllers.property.dtos.AddressDTO
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class LandLordUpdateTenantDTO(
    @field:NotNull(message = "O campo nome é obrigatório")
    @field:Size(min = 3, max = 40, message = "O campo nome deve ter entre 3  e 50 caracteres")
    val firstName: String? = "",

    @field:NotNull(message = "O campo sobrenome é obrigatório")
    @field:Size(min = 3, max = 40, message = "O campo sobrenome deve ter entre 3  e 50 caracteres")
    val lastName: String? = "",

    @field:NotNull(message = "O campo E-mail é obrigatório")
    @field:Email(message = "O E-mail informado é inválido")
    @field:Size(min = 3, max = 100, message = "O campo E-mail deve ter entre 3  e 100 caracteres")
    val email: String? = "",

    @field:NotNull(message = "O campo RG é obrigatório")
    @field:Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$", message = "O campo RG está é inválido")
    val rg: String? = "",

    @field:NotNull(message = "O campo CPF é obrigatório")
    @field:Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "O campo CPF está é inválido")
    val cpf: String? = "",

    @field:NotNull(message = "O campo data de nascimento é obrigatório")
    @field:Past(message = "O campo data de nascimento está inválido")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    val birthDate: LocalDate? = LocalDate.of(2000, 1, 1),

    @field:NotNull(message = "O campo nacionalidade é obrigatório")
    @field:Size(min = 3, max = 50, message = "O campo nacionalidade deve ter entre 3  e 50 caracteres")
    val nationality: String? = "",

    @field:NotNull(message = "O campo estado civil é obrigatório")
    val maritalStatus: MaritalStatusEnum? = MaritalStatusEnum.SINGLE,

    @field:Valid
    @field:NotNull(message = "O campo telefone é obrigatório")
    val telephones: TelephoneDTO? = TelephoneDTO(),

    @field:NotNull(message = "O campo trabalho é obrigatório")
    @field:Size(min = 8, max = 50, message = "O campo trabalho deve ter entre 3  e 50 caracteres")
    val job: String? = "",

    @field:Valid
    @field:NotNull(message = "O objeto endereço é obrigatório")
    val address: AddressDTO? = AddressDTO()
)