package com.imobly.imobly.persistences.property.repositories

import com.imobly.imobly.persistences.property.entities.PropertyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository: JpaRepository<PropertyEntity, String>