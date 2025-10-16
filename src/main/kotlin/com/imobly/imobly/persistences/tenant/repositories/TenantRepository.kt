package com.imobly.imobly.persistences.tenant.repositories

import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TenantRepository : JpaRepository<TenantEntity, String>