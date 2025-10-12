package com.imobly.imobly.services

import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.persistences.property.mappers.PropertyPersistenceMapper
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import org.springframework.stereotype.Service

@Service
class PropertyService(
    val repository: PropertyRepository,
    val mapper: PropertyPersistenceMapper
) {
    fun findAll(): List<PropertyDomain> {
        return mapper.toDomains(repository.findAll())
    }

    fun findById(id: String): PropertyDomain {
        return mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        }))
    }

    fun insert(property: PropertyDomain): PropertyDomain {
        return mapper.toDomain(repository.save(mapper.toEntity(property)))
    }

    fun update(id: String, property: PropertyDomain): PropertyDomain {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        })
        property.id = id
        return mapper.toDomain(repository.save(mapper.toEntity(property)))
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        })
        repository.deleteById(id)
    }
}
