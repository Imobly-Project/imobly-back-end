package com.imobly.imobly.persistences.landlord.mappers

import com.imobly.imobly.domains.users.LandLordDomain
import com.imobly.imobly.persistences.landlord.entities.LandLordEntity
import org.springframework.stereotype.Component

@Component
class LandLordPersistenceMapper {
    fun toDomain(landLord: LandLordEntity): LandLordDomain =
        LandLordDomain(
            landLord.id,
            landLord.firstName,
            landLord.lastName,
            landLord.email,
            landLord.telephones,
            landLord.password
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
            landLord.password
        )
}