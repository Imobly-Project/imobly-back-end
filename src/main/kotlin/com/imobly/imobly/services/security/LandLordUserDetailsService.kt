package com.imobly.imobly.services.security

import com.imobly.imobly.domains.users.RegisteredUserDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class LandLordUserDetailsService(
    private val repository: LandLordRepository,
    private val mapper: LandLordPersistenceMapper
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username != null) {
            return RegisteredUserDomain(
                mapper.toRegisteredUserDomain(repository.findByEmail(username)
                    .orElseThrow {
                        throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
                    })
            )
        }
        else throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
    }

}