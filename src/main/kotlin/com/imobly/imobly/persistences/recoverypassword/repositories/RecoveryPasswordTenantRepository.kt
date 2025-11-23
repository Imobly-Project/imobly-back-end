package com.imobly.imobly.persistences.recoverypassword.repositories

import com.imobly.imobly.persistences.recoverypassword.entities.RecoveryPasswordTenantEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RecoveryPasswordTenantRepository: JpaRepository<RecoveryPasswordTenantEntity, String> {
    fun findByTenant_Email(email: String): Optional<RecoveryPasswordTenantEntity>
}