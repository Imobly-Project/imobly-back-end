package com.imobly.imobly.persistences.property.mappers

import com.imobly.imobly.domains.AddressDomain
import com.imobly.imobly.persistences.property.entities.AddressEntity
import org.springframework.stereotype.Component

@Component
class AddressPersistenceMapper {

    fun toDomain(address: AddressEntity): AddressDomain {
        return AddressDomain(
            id = address.id,
            cep = address.cep,
            state = address.state,
            city = address.city,
            neighborhood = address.neighborhood,
            street = address.street,
            number = address.number,
            complement = address.complement
        )
    }

    fun toEntity(address: AddressDomain): AddressEntity {
        return AddressEntity(
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