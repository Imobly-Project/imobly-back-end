package com.imobly.imobly.persistences.lease.repositories

import com.imobly.imobly.persistences.landlord.entities.LandLordEntity
import com.imobly.imobly.persistences.lease.entities.LeaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LeaseRepository: JpaRepository<LeaseEntity, String> {
    fun findByTenant_FirstNameContainingOrTenant_LastNameContainingOrProperty_TitleContainingAllIgnoreCase(firstName: String, lastName: String, title: String): List<LeaseEntity>
}
