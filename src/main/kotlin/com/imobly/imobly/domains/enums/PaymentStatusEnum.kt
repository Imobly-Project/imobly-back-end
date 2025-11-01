package com.imobly.imobly.domains.enums

enum class PaymentStatusEnum(val description: String) {
    PAID("Pago"),
    PENDING("Pendente"),
    OVERDUE("Vencido")
}