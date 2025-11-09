package com.imobly.imobly.domains.reports

import com.imobly.imobly.domains.users.tenant.TenantDomain
import com.imobly.imobly.domains.enums.ReportStatusEnum
import java.time.LocalDateTime

class ReportDomain(
    var id: String? = null,
    var title: String,
    var message: String,
    var moment: LocalDateTime = LocalDateTime.now(),
    var status: ReportStatusEnum = ReportStatusEnum.NEW,
    var response: String = "",
    var tenant: TenantDomain
)
