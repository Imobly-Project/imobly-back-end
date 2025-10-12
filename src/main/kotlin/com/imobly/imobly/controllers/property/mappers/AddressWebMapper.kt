package com.imobly.imobly.controllers.property.mappers

import com.imobly.imobly.controllers.property.dtos.AddressDTO
import com.imobly.imobly.domains.AddressDomain
import org.springframework.stereotype.Component

@Component

class AddressWebMapper {
    fun toDomain(address: AddressDTO): AddressDomain {
        return AddressDomain(
            cep = address.cep ?: "",
            state = address.state ?: "",
            city = address.city ?: "",
            neighborhood = address.neighborhood ?: "",
            street = address.street ?: "",
            number = address.number ?: "",
            complement = address.complement ?: ""
        )
    }
    fun toDTO(address: AddressDomain): AddressDTO {
        return AddressDTO(
            cep = address.cep,
            state = address.state,
            city = address.city,
            neighborhood = address.neighborhood,
            street = address.street,
            number = address.number,
            complement = address.complement
        )
    }
}