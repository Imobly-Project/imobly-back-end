package com.imobly.imobly.controllers.issuereport.mappers

import com.imobly.imobly.controllers.issuereport.dtos.ComplaintDTO
import com.imobly.imobly.domains.reports.ComplaintDomain
import org.springframework.stereotype.Component

@Component
class ComplaintWebMapper {
    fun toDomain(complaint: ComplaintDTO): ComplaintDomain =
        ComplaintDomain(
            complaint.id,
            complaint.title ?: "",
            complaint.message ?: "",
            complaint.tenantId ?: ""
        )

    fun toDTO(complaint: ComplaintDomain): ComplaintDTO =
        ComplaintDTO(
            complaint.id,
            complaint.title,
            complaint.message,
            complaint.tenantId
        )
}