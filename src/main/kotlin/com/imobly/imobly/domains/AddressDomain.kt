package com.imobly.imobly.domains

class AddressDomain(
    var id: String? = null,
    var cep: String,
    var state: String,
    var city: String,
    var neighborhood: String,
    var street: String,
    var number: String,
    var complement: String
)