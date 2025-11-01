package com.imobly.imobly.domains.payments

import com.imobly.imobly.domains.leases.LeaseDomain

class PaymentDomain(
    var id: String? = null,
    var lease: LeaseDomain,
    var installments: List<MonthlyInstallmentDomain>
)