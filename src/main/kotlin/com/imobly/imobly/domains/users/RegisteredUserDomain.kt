package com.imobly.imobly.domains.users

// Necessário para futuras implementações (classes filhas: Tenant e LandLord)
open class RegisteredUserDomain(
    id: String?,
    firstName: String,
    lastName: String,
    email: String,
    telephones: List<String>,
    var password: String,
): UserDomain(id, firstName, lastName, email, telephones)
