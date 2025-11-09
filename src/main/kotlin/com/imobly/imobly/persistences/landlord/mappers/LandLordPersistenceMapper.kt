package com.imobly.imobly.persistences.landlord.mappers

import com.imobly.imobly.domains.users.landlord.LandLordDomain
import com.imobly.imobly.domains.users.RegisteredUserDomain
import com.imobly.imobly.persistences.landlord.entities.LandLordEntity
import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import org.springframework.stereotype.Component

@Component
class LandLordPersistenceMapper {
    fun toDomain(landLord: LandLordEntity): LandLordDomain =
        LandLordDomain(
            landLord.id,
            landLord.firstName,
            landLord.lastName,
            landLord.email,
            landLord.password,
            landLord.telephones,
            landLord.role
        )

    fun toDomains(landLords: List<LandLordEntity>): List<LandLordDomain> =
        landLords.map { toDomain(it) }

    fun toEntity(landLord: LandLordDomain): LandLordEntity =
        LandLordEntity(
            landLord.id,
            landLord.firstName,
            landLord.lastName,
            landLord.email,
            landLord.telephones,
            landLord.role,
            landLord.password
        )

    fun toRegisteredUserDomain(landLord: LandLordEntity): RegisteredUserDomain =
        RegisteredUserDomain(
            landLord.id,
            landLord.firstName,
            landLord.lastName,
            landLord.email,
            landLord.telephones,
            landLord.password,
            landLord.role
        )
}