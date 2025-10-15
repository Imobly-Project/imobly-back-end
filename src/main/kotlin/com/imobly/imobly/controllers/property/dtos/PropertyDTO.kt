package com.imobly.imobly.controllers.property.dtos

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PropertyDTO (
    val id: String?,
    @field:NotNull(message = "O campo título é obrigatório")
    @field:Size(min = 3, max = 50, message = "O campo título deve ter entre 3  e 50 caracteres")
    val title: String?,
    val pathImages: List<String>?,
    @field:NotNull(message = "O campo descrição é obrigatório")
    @field:Size(min = 3, max = 1_500, message = "O campo descrição deve ter entre 3  e 1 500 caracteres")
    val description: String?,
    @field:NotNull(message = "O campo aluguel é obrigatório")
    @field:Min(0, message = "O valor mínimo do campo aluguel é 0,00")
    @field:Max(1_000_000, message = "O valor máximo do campo aluguel é 1 000 000,00")
    val rentalValue: Double?,
    @field:NotNull(message = "O campo área é obrigatório")
    @field:Min(0, message = "O valor mínimo do campo área é 0,00 m²")
    @field:Max(2_000_000, message = "O valor máximo do campo área é 2 000 000,00 m²")
    val area: Float?,
    @field:Min(0, message = "O valor mínimo do campo quartos é 0")
    @field:Max(5_000, message = "O valor máximo do campo quartos é 5 000")
    val bedrooms: Int?,
    @field:Min(0, message = "O valor mínimo do campo banheiros é 0")
    @field:Max(5_000, message = "O valor máximo do campo banheiros é 5 000")
    val bathrooms: Int?,
    @field:Min(0, message = "O valor mínimo do campo espaços de garagem é 0")
    @field:Max(5_000, message = "O valor máximo do campo espaços de garagem é 5 000")
    val garageSpaces: Int?,
    @field:Valid
    @field:NotNull(message = "O objeto endereço é obrigatório")
    val address: AddressDTO?
)