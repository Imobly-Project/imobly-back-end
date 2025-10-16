package com.imobly.imobly.persistences.tenant.repositories

import com.imobly.imobly.persistences.tenant.entities.UserRegisteredEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRegisteredRepository : JpaRepository<UserRegisteredEntity, String>