package com.imobly.imobly.services

import com.imobly.imobly.domains.CategoryDomain
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.category.mappers.CategoryPersistenceMapper
import com.imobly.imobly.persistences.category.repositories.CategoryRepository
import com.imobly.imobly.persistences.property.mappers.AddressPersistenceMapper
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class CategoryService(
    private val repository: CategoryRepository, private val mapper: CategoryPersistenceMapper
) {
    fun findAllByTitle(title: String): List<CategoryDomain> {
        val list = mapper.toDomains(repository.findByTitleContainingAllIgnoreCase(title))
        Collections.sort(list)
        return list
    }

    fun findById(id: String): CategoryDomain =
        mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        }), PropertyPersistenceMapper(AddressPersistenceMapper()))

    fun insert(category: CategoryDomain): CategoryDomain {
        val categorySaved = repository.save(
            mapper.toEntity(category, PropertyPersistenceMapper(AddressPersistenceMapper()))
        )
        return mapper.toDomain(categorySaved, PropertyPersistenceMapper(AddressPersistenceMapper()))
    }

    fun update(id: String, category: CategoryDomain): CategoryDomain {
        val categoryFound = mapper.toDomain(
            repository.findById(id).orElseThrow {
                throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
            }, PropertyPersistenceMapper(AddressPersistenceMapper())
        )
        categoryFound.title = category.title
        val categoryUpdated = repository.save(
            mapper.toEntity(categoryFound, PropertyPersistenceMapper(AddressPersistenceMapper()))
        )
        return mapper.toDomain(categoryUpdated, PropertyPersistenceMapper(AddressPersistenceMapper()))
    }

    fun delete(id: String) {
        val category = repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0014)
        }
        if (category.properties.isNotEmpty()) {
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0026)
        }
        repository.deleteById(id)
    }
}