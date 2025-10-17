package com.imobly.imobly.domains

data class UserRegisteredDomain(
    val id: String?,
    val name: String,
    val email: String,
    val password: String
)