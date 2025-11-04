package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class TenantService(
    val repository: TenantRepository, val uploadService: UploadService, val mapper: TenantPersistenceMapper
) {

    fun findAll(): List<TenantDomain> = mapper.toDomains(repository.findAll())

    fun findById(id: String): TenantDomain =
        mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        }))

    fun insert(tenant: TenantDomain, file: MultipartFile?): TenantDomain {
        tenant.role = UserRoleEnum.TENANT
        checkUniqueFields(tenant)
        uploadService.checkIfMultipartFileIsNull(file)
        tenant.pathImage = uploadService.uploadImage(file!!)
        val tenantSaved = repository.save(mapper.toEntity(tenant))
        return mapper.toDomain(tenantSaved)
    }

    fun update(id: String, tenant: TenantDomain, file: MultipartFile?): TenantDomain {
        tenant.pathImage = repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        }.pathImage
        checkUniqueFields(tenant, id)
        tenant.id = id
        if (file != null) {
            tenant.pathImage = uploadService.uploadImage(file)
        }
        val tenantUpdated = repository.save(mapper.toEntity(tenant))
        return mapper.toDomain(tenantUpdated)
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })
        repository.deleteById(id)
    }

    fun checkUniqueFields(tenant: TenantDomain, id: String = "") {
        repository.findByEmail(tenant.email).ifPresent {
            if (it.email == tenant.email && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
        }
        repository.findByCpf(tenant.cpf).ifPresent {
            if (it.cpf == tenant.cpf && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0006)
        }
        repository.findByRg(tenant.rg).ifPresent {
            if (it.rg == tenant.rg && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0007)
        }
    }
}