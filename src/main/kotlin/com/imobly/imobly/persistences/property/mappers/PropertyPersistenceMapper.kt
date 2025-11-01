package com.imobly.imobly.persistences.property.mappers

import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.persistences.category.mappers.CategoryPersistenceMapper
import com.imobly.imobly.persistences.property.entities.PropertyEntity
import org.springframework.stereotype.Component

@Component
class PropertyPersistenceMapper(val addressMapper: AddressPersistenceMapper) {
    fun toDomains(properties: List<PropertyEntity>): List<PropertyDomain> =
        properties.map {
            toDomain(it, CategoryPersistenceMapper())
        }

    fun toEntity(property: PropertyDomain, categoryMapper: CategoryPersistenceMapper): PropertyEntity =
        PropertyEntity(
            id = property.id,
            title = property.title,
            pathImages = property.pathImages,
            description = property.description,
            monthlyRent = property.monthlyRent,
            area = property.area,
            bathrooms = property.bathrooms,
            bedrooms = property.bedrooms,
            garageSpaces = property.garageSpaces,
            address = addressMapper.toEntity(property.address),
            category = categoryMapper.toEntity(property.category)
        )

    fun toDomain(property: PropertyEntity, categoryMapper: CategoryPersistenceMapper): PropertyDomain =
        PropertyDomain(
            id = property.id,
            title = property.title,
            pathImages = property.pathImages,
            description = property.description,
            monthlyRent = property.monthlyRent,
            area = property.area,
            bathrooms = property.bathrooms,
            bedrooms = property.bedrooms,
            garageSpaces = property.garageSpaces,
            address = addressMapper.toDomain(property.address),
            category = categoryMapper.toDomainWithoutProperties(property.category)
        )
}