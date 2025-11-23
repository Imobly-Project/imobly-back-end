package com.imobly.imobly.persistences.changeemail.mappers

import com.imobly.imobly.domains.changeemail.ChangeEmailLandLordDomain
import com.imobly.imobly.domains.changeemail.ChangeEmailTenantDomain
import com.imobly.imobly.persistences.changeemail.entities.ChangeEmailLandLordEntity
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.changeemail.entities.ChangeEmailTenantEntity
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import org.springframework.stereotype.Component

@Component
class ChangeEmailPersistenceMapper(
    private val tenantMapper: TenantPersistenceMapper,
    private val landLordMapper: LandLordPersistenceMapper
) {
    fun toDomain(entity: ChangeEmailTenantEntity): ChangeEmailTenantDomain =
        ChangeEmailTenantDomain(
            entity.id,
            tenantMapper.toDomain(entity.tenant),
            entity.token,
            entity.email,
            entity.moment
        )

    fun toEntity(entity: ChangeEmailTenantDomain): ChangeEmailTenantEntity =
        ChangeEmailTenantEntity(
            entity.id,
            tenantMapper.toEntity(entity.tenant),
            entity.email,
            entity.token,
            entity.moment
        )

    fun toDomain(entity: ChangeEmailLandLordEntity): ChangeEmailLandLordDomain =
        ChangeEmailLandLordDomain(
            entity.id,
            landLordMapper.toDomain(entity.landLord),
            entity.token,
            entity.email,
            entity.moment
        )

    fun toEntity(entity: ChangeEmailLandLordDomain): ChangeEmailLandLordEntity =
        ChangeEmailLandLordEntity(
            entity.id,
            landLordMapper.toEntity(entity.landLord),
            entity.email,
            entity.token,
            entity.moment
        )
}