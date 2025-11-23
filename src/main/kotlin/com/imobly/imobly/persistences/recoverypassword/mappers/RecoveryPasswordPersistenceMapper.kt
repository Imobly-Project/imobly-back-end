package com.imobly.imobly.persistences.recoverypassword.mappers

import com.imobly.imobly.domains.recoverypassword.RecoveryPasswordLandLordDomain
import com.imobly.imobly.domains.recoverypassword.RecoveryPasswordTenantDomain
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.recoverypassword.entities.RecoveryPasswordLandLordEntity
import com.imobly.imobly.persistences.recoverypassword.entities.RecoveryPasswordTenantEntity
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import org.springframework.stereotype.Component

@Component
class RecoveryPasswordPersistenceMapper(
    private val tenantMapper: TenantPersistenceMapper,
    private val landLordMapper: LandLordPersistenceMapper
) {

    fun toDomain(entity: RecoveryPasswordTenantEntity): RecoveryPasswordTenantDomain =
        RecoveryPasswordTenantDomain(
            entity.id,
            tenantMapper.toDomain(entity.tenant),
            entity.token,
            entity.moment
        )

    fun toEntity(entity: RecoveryPasswordTenantDomain): RecoveryPasswordTenantEntity =
        RecoveryPasswordTenantEntity(
            entity.id,
            tenantMapper.toEntity(entity.tenant),
            entity.token,
            entity.moment
        )

    fun toDomain(entity: RecoveryPasswordLandLordEntity): RecoveryPasswordLandLordDomain =
        RecoveryPasswordLandLordDomain(
            entity.id,
            landLordMapper.toDomain(entity.landLord),
            entity.token,
            entity.moment
        )

    fun toEntity(entity: RecoveryPasswordLandLordDomain): RecoveryPasswordLandLordEntity =
        RecoveryPasswordLandLordEntity(
            entity.id,
            landLordMapper.toEntity(entity.landLord),
            entity.token,
            entity.moment
        )
}