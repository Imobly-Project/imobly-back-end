package com.imobly.imobly.domains.users

// Necessário para futuras implementações (classes filhas: RegisteredUser e Guest)
abstract class UserDomain(
    var id: String?,
    var firstName: String,
    var lastName: String,
    var email: String,
    var telephones: List<String>
)