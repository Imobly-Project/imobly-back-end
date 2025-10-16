package com.imobly.imobly.persistences.tenant.mappers

import com.imobly.imobly.domains.UserRegisteredDomain
import com.imobly.imobly.persistences.tenant.entities.UserRegisteredEntity
import com.imobly.imobly.persistences.tenant.entities.UserEntity

object UserRegisteredPersistenceMapper {
    fun toDomain(entity: UserRegisteredEntity): UserRegisteredDomain =
        UserRegisteredDomain(id = entity.id, name = entity.name, email = entity.email, password = entity.password)

    fun toEntity(domain: UserRegisteredDomain): UserRegisteredEntity =
        UserRegisteredEntity(id = domain.id, name = domain.name, email = domain.email, password = domain.password)
}