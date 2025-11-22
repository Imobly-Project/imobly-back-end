package com.imobly.imobly.persistences.issuereport.repositories

import com.imobly.imobly.persistences.issuereport.entities.ReportEntity
import org.springframework.context.annotation.Description
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<ReportEntity, String> {
    fun findByTitleContainingOrMessageContainingAllIgnoreCase(title: String, message: String): List<ReportEntity>

    fun findByTenant_IdAndTitleContainingIgnoreCaseOrTenant_IdAndMessageContainingIgnoreCase(id1: String, title: String, id2: String, message: String): List<ReportEntity>
}