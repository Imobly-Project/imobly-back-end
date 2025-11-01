package com.imobly.imobly.domains.leases

import java.time.LocalDate

class LeaseAgreementDomain(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val propertyId: String,
    val tenantId: String,
    val monthlyRent: Double,
    val securityDeposit: Double,
    val paymentDueDay: Int
)