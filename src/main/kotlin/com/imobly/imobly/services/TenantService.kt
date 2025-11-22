package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.Collections

@Service
class TenantService(
    private val tenantRepository: TenantRepository,
    private val leaseRepository: LeaseRepository,
    private val uploadService: UploadService,
    private val tenantMapper: TenantPersistenceMapper
) {

    fun findAllByNameOrCpf(nameOrCpf: String): List<TenantDomain>  {
        val list = tenantMapper.toDomains(
            tenantRepository.findByFirstNameContainingOrLastNameContainingOrCpfContainingAllIgnoreCase(
                nameOrCpf, nameOrCpf, nameOrCpf
            )
        )
        Collections.sort(list)
        return list
    }

    fun findById(id: String): TenantDomain =
        tenantMapper.toDomain(tenantRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })

    fun findByEmail(email: String): TenantDomain =
        tenantMapper.toDomain(tenantRepository.findByEmail(email).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })

    fun createAccount(tenant: TenantDomain, file: MultipartFile?): TenantDomain {
        tenant.role = UserRoleEnum.TENANT
        tenant.passwd = BCryptPasswordEncoder().encode(tenant.password)
        checkUniqueFields(tenant)
        uploadService.checkIfMultipartFileIsNull(file)
        tenant.pathImage = uploadService.uploadImage(file!!)
        val tenantSaved = tenantRepository.save(tenantMapper.toEntity(tenant))
        return tenantMapper.toDomain(tenantSaved)
    }

    fun landLordUpdate(id: String, tenant: TenantDomain, file: MultipartFile?): TenantDomain {
        val tenantFound = tenantMapper.toDomain(tenantRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })

        tenantFound.firstName = tenant.firstName
        tenantFound.lastName = tenant.lastName
        tenantFound.cpf = tenant.cpf
        tenantFound.rg = tenant.rg
        tenantFound.email = tenant.email
        tenantFound.nationality = tenant.nationality
        tenantFound.maritalStatus = tenant.maritalStatus
        tenantFound.job = tenant.job
        tenantFound.telephones = tenant.telephones
        tenantFound.birthDate = tenant.birthDate
        tenantFound.address = tenant.address

        checkUniqueFields(tenant, id)
        if (file != null)
            tenantFound.pathImage = uploadService.uploadImage(file)

        val tenantUpdated = tenantRepository.save(tenantMapper.toEntity(tenantFound))
        return tenantMapper.toDomain(tenantUpdated)
    }

    fun selfUpdate(id: String, tenant: TenantDomain, file: MultipartFile?): TenantDomain {
        val tenantFound = tenantMapper.toDomain(tenantRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
        })
        checkUniqueFieldsOnlyEmail(tenant, id)
        tenantFound.email = tenant.email
        tenantFound.telephones = tenant.telephones
        if (file != null)
            tenantFound.pathImage = uploadService.uploadImage(file)

        val tenantUpdated = tenantRepository.save(tenantMapper.toEntity(tenantFound))
        return tenantMapper.toDomain(tenantUpdated)
    }

    fun delete(id: String) {
        if (!tenantRepository.existsById(id))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)

        if (leaseRepository.existsByTenant_Id(id))
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0020)

        tenantRepository.deleteById(id)
    }

    private fun checkUniqueFields(tenant: TenantDomain, id: String = "") {
        if (tenantRepository.existsByEmailAndIdNot(tenant.email, id))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)

        if (tenantRepository.existsByCpfAndIdNot(tenant.cpf, id))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0006)

        if (tenantRepository.existsByRgAndIdNot(tenant.rg, id))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0007)
    }

    private fun checkUniqueFieldsOnlyEmail(tenant: TenantDomain, id: String = "") {
        if (tenantRepository.existsByEmailAndIdNot(tenant.email, id))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
    }
}