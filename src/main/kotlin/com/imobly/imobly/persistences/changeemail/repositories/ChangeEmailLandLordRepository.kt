package com.imobly.imobly.persistences.changeemail.repositories

import com.imobly.imobly.persistences.changeemail.entities.ChangeEmailLandLordEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ChangeEmailLandLordRepository: JpaRepository<ChangeEmailLandLordEntity, String> {
    fun findByLandLord_Id(id: String): Optional<ChangeEmailLandLordEntity>
}