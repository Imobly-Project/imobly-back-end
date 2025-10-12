package com.imobly.imobly.persistences.property.mappers

import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.persistences.property.entities.PropertyEntity
import org.springframework.stereotype.Component

@Component
class PropertyPersistenceMapper(
    val addressMapper: AddressPersistenceMapper
) {
    fun toDomains(properties: List<PropertyEntity>): List<PropertyDomain> {
        return properties.map {
            toDomain(it)
        }
    }

    fun toEntity(property: PropertyDomain): PropertyEntity {
        return PropertyEntity(
            id = property.id,
            title = property.title,
            pathImages = property.pathImages,
            description = property.description,
            rentalValue = property.rentalValue,
            area = property.area,
            bathrooms = property.bathrooms,
            bedrooms = property.bedrooms,
            garageSpaces = property.garageSpaces,
            address = addressMapper.toEntity(property.address)
        )
    }

    fun toDomain(property: PropertyEntity): PropertyDomain {
        return PropertyDomain(
            id = property.id,
            title = property.title,
            pathImages = property.pathImages,
            description = property.description,
            rentalValue = property.rentalValue,
            area = property.area,
            bathrooms = property.bathrooms,
            bedrooms = property.bedrooms,
            garageSpaces = property.garageSpaces,
            address = addressMapper.toDomain(property.address)
        )
    }
}