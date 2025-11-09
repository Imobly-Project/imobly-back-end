package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.tenant.LandLordUpdateTenantDomain
import com.imobly.imobly.domains.users.tenant.SelfUpdateTenantDomain
import com.imobly.imobly.domains.users.tenant.TenantDomain
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class TenantService(
    private val repository: TenantRepository,
    private val uploadService: UploadService,
    private val tenantMapper: TenantPersistenceMapper
) {

    fun findAll(): List<TenantDomain> = tenantMapper.toDomains(repository.findAll())

    fun findById(id: String): TenantDomain =
        tenantMapper.toDomain(repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })

    fun findByEmail(email: String): TenantDomain =
        tenantMapper.toDomain(repository.findByEmail(email).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })

    fun insert(tenant: TenantDomain, file: MultipartFile?): TenantDomain {
        tenant.role = UserRoleEnum.TENANT
        tenant.passwd = BCryptPasswordEncoder().encode(tenant.password)
        checkUniqueFields(tenant)
        uploadService.checkIfMultipartFileIsNull(file)
        tenant.pathImage = uploadService.uploadImage(file!!)
        val tenantSaved = repository.save(tenantMapper.toEntity(tenant))
        return tenantMapper.toDomain(tenantSaved)
    }

    fun landLordUpdate(id: String, tenant: LandLordUpdateTenantDomain, file: MultipartFile?): TenantDomain {
        val tenantInDatabase = tenantMapper.toDomain(repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })

        tenantInDatabase.firstName = tenant.firstName
        tenantInDatabase.lastName = tenant.lastName
        tenantInDatabase.cpf = tenant.cpf
        tenantInDatabase.rg = tenant.rg
        tenantInDatabase.email = tenant.email
        tenantInDatabase.nationality = tenant.nationality
        tenantInDatabase.maritalStatus = tenant.maritalStatus
        tenantInDatabase.job = tenant.job
        tenantInDatabase.telephones = tenant.telephones
        tenantInDatabase.birthDate = tenant.birthDate
        tenantInDatabase.address = tenant.address

        checkUniqueFields(tenant, id)
        if (file != null)
            tenantInDatabase.pathImage = uploadService.uploadImage(file)

        val tenantUpdated = repository.save(tenantMapper.toEntity(tenantInDatabase))
        return tenantMapper.toDomain(tenantUpdated)
    }

    fun selfUpdate(id: String, restrictedTenant: SelfUpdateTenantDomain, file: MultipartFile?): TenantDomain {
        val tenant = tenantMapper.toDomain(repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })
        checkUniqueFields(restrictedTenant, id)
        tenant.email = restrictedTenant.email
        tenant.telephones = restrictedTenant.telephones
        if (file != null) {
            tenant.pathImage = uploadService.uploadImage(file)
        }
        val tenantUpdated = repository.save(tenantMapper.toEntity(tenant))
        return tenantMapper.toDomain(tenantUpdated)
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })
        repository.deleteById(id)
    }

    private fun checkUniqueFields(tenant: TenantDomain, id: String = "") {
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

    private fun checkUniqueFields(tenant: SelfUpdateTenantDomain, id: String = "") {
        repository.findByEmail(tenant.email).ifPresent {
            if (it.email == tenant.email && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
        }
    }

    private fun checkUniqueFields(tenant: LandLordUpdateTenantDomain, id: String = "") {
        repository.findByEmail(tenant.email).ifPresent {
            if (it.email == tenant.email && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
        }
    }
}