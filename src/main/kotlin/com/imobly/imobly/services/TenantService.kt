package com.imobly.imobly.services

import com.imobly.imobly.domains.TenantDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.stereotype.Service

@Service
class TenantService(
    val repository: TenantRepository,
    val mapper: TenantPersistenceMapper
) {

    fun findAll(): List<TenantDomain> {
        return mapper.toDomains(repository.findAll())
    }
    fun findById(id: String): TenantDomain {
        return mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
        }))
    }

    fun insert(tenant: TenantDomain): TenantDomain {
        val tenantSaved = repository.save(mapper.toEntity(tenant))
        return mapper.toDomain(tenantSaved)
    }
    fun update(id: String, tenant: TenantDomain): TenantDomain {
       tenant.id = id
        val tenantUpdated = repository.save(mapper.toEntity(tenant))
        return mapper.toDomain(tenantUpdated)
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
        throw ResourceNotFoundException(RuntimeErrorEnum.ERR0002)
    })
        repository.deleteById(id)
    }
}