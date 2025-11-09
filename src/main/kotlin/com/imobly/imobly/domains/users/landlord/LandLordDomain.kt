package com.imobly.imobly.domains.users.landlord

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.RegisteredUserDomain

class LandLordDomain(
    id: String? = null,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    telephones: List<String>,
    role: UserRoleEnum
): RegisteredUserDomain(id, firstName, lastName, email, telephones, password, role)