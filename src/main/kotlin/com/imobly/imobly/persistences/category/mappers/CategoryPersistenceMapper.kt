package com.imobly.imobly.persistences.category.mappers

import com.imobly.imobly.domains.CategoryDomain
import com.imobly.imobly.persistences.category.entities.CategoryEntity
import com.imobly.imobly.persistences.property.mappers.AddressPersistenceMapper
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import org.springframework.stereotype.Component

@Component
class CategoryPersistenceMapper {
    fun toDomain(category: CategoryEntity, mapper: PropertyPersistenceMapper): CategoryDomain =
        CategoryDomain(category.id, category.title, mapper.toDomains(category.properties))

    fun toDomainWithoutProperties(category: CategoryEntity): CategoryDomain =
        CategoryDomain(category.id, category.title, emptyList())

    fun toEntityWithoutProperties(category: CategoryDomain): CategoryEntity =
        CategoryEntity(category.id, category.title, emptyList())

    fun toDomains(categories: List<CategoryEntity>): List<CategoryDomain> =
        categories.map { toDomain(it, PropertyPersistenceMapper(AddressPersistenceMapper())) }

    fun toEntity(category: CategoryDomain, mapper: PropertyPersistenceMapper): CategoryEntity =
        CategoryEntity(category.id, category.title, mapper.toEntities(category.properties))
}