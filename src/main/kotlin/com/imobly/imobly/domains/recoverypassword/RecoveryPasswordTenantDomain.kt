package com.imobly.imobly.domains.recoverypassword

import com.imobly.imobly.domains.users.TenantDomain
import java.time.LocalDateTime

class RecoveryPasswordTenantDomain(
    var id: String? = null,

    var tenant: TenantDomain,

    var  token: String,

    var moment: LocalDateTime = LocalDateTime.now()
)