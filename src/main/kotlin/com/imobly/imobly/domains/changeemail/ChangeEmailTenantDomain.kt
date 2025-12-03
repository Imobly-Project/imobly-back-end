package com.imobly.imobly.domains.changeemail

import com.imobly.imobly.domains.users.TenantDomain
import java.time.LocalDateTime

class ChangeEmailTenantDomain(
    var id: String? = null,
    var tenant: TenantDomain,
    var token: String,
    var email: String,
    var moment: LocalDateTime = LocalDateTime.now()
)