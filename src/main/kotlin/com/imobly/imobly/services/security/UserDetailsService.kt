package com.imobly.imobly.services.security

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.RegisteredUserDomain
import com.imobly.imobly.domains.users.tenant.TenantDomain
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
class UserDetailsService(
    private val landLordRepository: LandLordRepository,
    private val tenantRepository: TenantRepository,
    private val landLordMapper: LandLordPersistenceMapper,
    private val tenantMapper: TenantPersistenceMapper
): UserDetailsService {

    var role = UserRoleEnum.TENANT

    override fun loadUserByUsername(username: String): UserDetails {
        if (role == UserRoleEnum.TENANT) {
            val tenant = tenantRepository.findByEmail(username)
            var user: RegisteredUserDomain = TenantDomain()
            tenant.ifPresentOrElse(
                {
                    user = RegisteredUserDomain(tenantMapper.toRegisteredUserDomain(it))
                },
                {
                    throw ResourceNotFoundException(RuntimeErrorEnum.ERR0012)
                }
            )
            return user
        }
        val landLord = landLordRepository.findByEmail(username)
        var user: RegisteredUserDomain = TenantDomain()
        landLord.ifPresentOrElse(
            {
                user = RegisteredUserDomain(landLordMapper.toRegisteredUserDomain(it))
            },
            {
                throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
            }
        )
        return user
    }
}