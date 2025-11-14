package com.imobly.imobly.persistences.issuereport.repositories

import com.imobly.imobly.persistences.issuereport.entities.ReportEntity
import org.springframework.context.annotation.Description
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<ReportEntity, String> {
    fun findByTitleContainingOrMessageContainingAllIgnoreCase(title: String, message: String): List<ReportEntity>
}