package com.imobly.imobly.persistences.landlord.repositories

import com.imobly.imobly.persistences.landlord.entities.LandLordEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LandLordRepository: JpaRepository<LandLordEntity, String> {
    fun findByEmail(email: String): Optional<LandLordEntity>

}