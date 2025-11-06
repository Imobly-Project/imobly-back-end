package com.imobly.imobly.services

import com.imobly.imobly.domains.CategoryDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.category.mappers.CategoryPersistenceMapper
import com.imobly.imobly.persistences.category.repositories.CategoryRepository
import com.imobly.imobly.persistences.property.mappers.AddressPersistenceMapper
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository, private val mapper: CategoryPersistenceMapper
) {
    fun findAll(): List<CategoryDomain> = mapper.toDomains(repository.findAll())

    fun findById(id: String): CategoryDomain =
        mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        }), PropertyPersistenceMapper(AddressPersistenceMapper()))


    fun insert(category: CategoryDomain): CategoryDomain {
        val categorySaved = repository.save(mapper.toEntity(category))
        return mapper.toDomain(categorySaved, PropertyPersistenceMapper(AddressPersistenceMapper()))
    }

    fun update(id: String, category: CategoryDomain): CategoryDomain {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        })
        category.id = id
        val categoryUpdated = repository.save(mapper.toEntity(category))
        return mapper.toDomain(categoryUpdated, PropertyPersistenceMapper(AddressPersistenceMapper()))
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        })
        repository.deleteById(id)
    }
}