package com.imobly.imobly.services.security

import com.imobly.imobly.domains.users.RegisteredUserDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class TenantUserDetailsService(
    private val tenantRepository: TenantRepository,
    private val tenantMapper: TenantPersistenceMapper
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username != null) {
            return RegisteredUserDomain(
                tenantMapper.toRegisteredUserDomain(tenantRepository.findByEmail(username)
                    .orElseThrow {
                        throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
                    })
            )
        }
        else throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
    }

}