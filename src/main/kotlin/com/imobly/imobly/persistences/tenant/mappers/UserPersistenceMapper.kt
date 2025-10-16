package com.imobly.imobly.persistences.tenant.mappers

import com.imobly.imobly.domains.UserDomain
import com.imobly.imobly.persistences.tenant.entities.UserEntity

object UserPersistenceMapper {
    fun toDomain(entity: UserEntity): UserDomain =
        UserDomain(id = entity.id, name = entity.name, email = entity.email)

    fun toEntity(domain: UserDomain): UserEntity =
        UserEntity(id = domain.id, name = domain.name, email = domain.email)
}