package com.imobly.imobly.persistences.changeemail.repositories

import com.imobly.imobly.persistences.changeemail.entities.ChangeEmailTenantEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ChangeEmailTenantRepository: JpaRepository<ChangeEmailTenantEntity, String> {
    fun findByTenant_Id(email: String): Optional<ChangeEmailTenantEntity>
}