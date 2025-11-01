package com.imobly.imobly.domains.reports

class ComplaintDomain(
    var id: String? = null,
    var title: String,
    var message: String,
    var tenantId: String
)