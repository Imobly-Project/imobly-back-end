package com.imobly.imobly.controllers.property.mappers

import com.imobly.imobly.controllers.property.dtos.AddressDTO
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.controllers.property.dtos.PropertyDTO
import org.springframework.stereotype.Component

@Component
class PropertyWebMapper(
    val addressMapper: AddressWebMapper
) {

    fun toDomain(property: PropertyDTO): PropertyDomain {
        return PropertyDomain(
            title = property.title ?: "",
            pathImages = property.pathImages ?: mutableListOf(),
            description = property.description ?: "",
            rentalValue = property.rentalValue ?: 0.0,
            area = property.area ?: 0.0f,
            bathrooms = property.bathrooms ?: 0,
            bedrooms = property.bedrooms ?: 0,
            garageSpaces = property.garageSpaces ?: 0,
            address = addressMapper.toDomain(property.address ?: AddressDTO())
        )
    }

    fun toDTO(property: PropertyDomain): PropertyDTO {
        return PropertyDTO(
            id = property.id,
            title = property.title,
            pathImages = property.pathImages,
            description = property.description,
            rentalValue = property.rentalValue,
            area = property.area,
            bathrooms = property.bathrooms,
            bedrooms = property.bedrooms,
            garageSpaces = property.garageSpaces,
            address = addressMapper.toDTO(property.address)
        )
    }

    fun toDTOs(properties: List<PropertyDomain>): List<PropertyDTO> {
        return properties.map {
            toDTO(it)
        }
    }

}