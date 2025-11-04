package com.imobly.imobly.domains.users

import com.imobly.imobly.domains.enums.UserRoleEnum
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

// Necessário para futuras implementações (classes filhas: Tenant e LandLord)
open class RegisteredUserDomain(
    id: String?,
    firstName: String,
    lastName: String,
    email: String,
    telephones: List<String>,
    var passwd: String,
    var role: UserRoleEnum,
): UserDomain(id, firstName, lastName, email, telephones), UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?>? =
        if (role == UserRoleEnum.LAND_LORD)
            listOf(SimpleGrantedAuthority("ROLE_LAND_LORD"), SimpleGrantedAuthority("ROLE_TENANT"))
        else
            listOf(SimpleGrantedAuthority("ROLE_TENANT"))

    override fun getPassword(): String = passwd

    override fun getUsername(): String = email

    constructor(user: RegisteredUserDomain) : this(
        user.id, user.firstName, user.lastName, user.email, user.telephones, user.password, user.role
    )
}
