package com.imobly.imobly.services.changeemail

import com.imobly.imobly.domains.changeemail.ChangeEmailTenantDomain
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.changeemail.mappers.ChangeEmailPersistenceMapper
import com.imobly.imobly.persistences.changeemail.repositories.ChangeEmailTenantRepository
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import com.imobly.imobly.services.EmailService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TenantChangeEmailService(
    private val tenantRepository: TenantRepository,
    private val tenantMapper: TenantPersistenceMapper,
    private val changeEmailRepository: ChangeEmailTenantRepository,
    private val changeEmailMapper: ChangeEmailPersistenceMapper,
    private val service: EmailService
) {

    fun sendCodeForUpdateEmail(id: String, tenant: TenantDomain) {
        val token =  String.format("%06d", (0..999999).random())
        changeEmailRepository.findByTenant_Id(id).ifPresentOrElse(
            {
                val changeEmail = changeEmailMapper.toDomain(it)
                changeEmail.token = token
                changeEmail.email = tenant.email
                changeEmail.moment = LocalDateTime.now().plusMinutes(10)
                checkIfEmailAlreadyExists(tenant.email)
                changeEmailRepository.save(changeEmailMapper.toEntity(changeEmail))
            },
            {
                val tenantFound = tenantRepository.findById(id).orElseThrow {
                    throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
                }
                val changeEmail = ChangeEmailTenantDomain(
                    token = token,
                    tenant = tenantMapper.toDomain(tenantFound),
                    email = tenant.email,
                    moment = LocalDateTime.now().plusMinutes(10)
                )
                checkIfEmailAlreadyExists(tenant.email)
                changeEmailRepository.save(changeEmailMapper.toEntity(changeEmail))
            }
        )

        service.sendEmail(tenant.email, "Troca de E-mail", "Codigo de confirmação: $token")
    }

    fun updateEmail(id: String, code: String) {
        if ( !validateToken(id, code) ) {
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0021)
        }
        val tenantFound = tenantMapper.toDomain(tenantRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })
        val changeEmail = changeEmailRepository.findByTenant_Id(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        }
        tenantFound.email = changeEmail.email
        changeEmailRepository.delete(changeEmail)
        checkIfEmailAlreadyExists(changeEmail.email)
        tenantRepository.save(tenantMapper.toEntity(tenantFound))
    }

    private fun validateToken(id: String, token: String): Boolean {
        val changeEmail = changeEmailRepository.findByTenant_Id(id).orElse(null) ?: return false

        if (changeEmail.moment.isBefore(LocalDateTime.now())) {
            changeEmailRepository.delete(changeEmail)
            return false
        }

        if (changeEmail.token != token) {
            changeEmailRepository.delete(changeEmail)
            return false
        }
        return true
    }

    private fun checkIfEmailAlreadyExists(email: String) {
        if (tenantRepository.existsByEmail(email))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
    }
}