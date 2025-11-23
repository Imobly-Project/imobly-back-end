package com.imobly.imobly.persistences.recoverypassword.repositories

import com.imobly.imobly.persistences.recoverypassword.entities.RecoveryPasswordLandLordEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RecoveryPasswordLandLordRepository: JpaRepository<RecoveryPasswordLandLordEntity, String> {
    fun findByLandLord_Email(email: String): Optional<RecoveryPasswordLandLordEntity>
}