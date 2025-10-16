package com.imobly.imobly.domains
class TenantDomain(
    var id: String? = null,
    var name: String,
    var email: String,
    var password: String,
    var rg: String,
    var cpf: String,
    var birthDate: String,
    var nationality: String,
    var maritalStatus: String
)