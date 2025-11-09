package com.imobly.imobly.domains.leases

import com.imobly.imobly.domains.PropertyDomain
import com.imobly.imobly.domains.users.tenant.TenantDomain
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class LeaseDomain(
    var id: String? = null,
    var startDate: LocalDate,
    var endDate: LocalDate,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var lastUpdatedAt: LocalDateTime = LocalDateTime.now(),
    var property: PropertyDomain,
    var tenant: TenantDomain,
    var durationInMonths: Long = ChronoUnit.MONTHS.between(startDate, LocalDate.now()),
    var monthlyRent: Double,
    var securityDeposit: Double,
    var paymentDueDay: Int,
    var isEnabled: Boolean = true
)

