package com.imobly.imobly.domains.changeemail

import com.imobly.imobly.domains.users.TenantDomain
import java.time.LocalDateTime

class ChangeEmailTenantDomain(
    val id: String? = null,
    val tenant: TenantDomain,
    val token: String,
    val email: String,
    val moment: LocalDateTime = LocalDateTime.now()
)