package com.imobly.imobly.persistences.lease.repositories

import com.imobly.imobly.persistences.landlord.entities.LandLordEntity
import com.imobly.imobly.persistences.lease.entities.LeaseEntity
import com.imobly.imobly.persistences.property.entities.PropertyEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LeaseRepository: JpaRepository<LeaseEntity, String> {
    fun findByTenant_FirstNameContainingOrTenant_LastNameContainingOrProperty_TitleContainingAllIgnoreCase(firstName: String, lastName: String, title: String): List<LeaseEntity>

    fun existsByTenant_Id(id: String): Boolean

    fun findByTenant_IdAndProperty_TitleContainingIgnoreCase(id: String, title: String): List<LeaseEntity>

    fun existsByTenant_IdAndProperty_Id(tenantId: String, propertyId: String): Boolean

}
