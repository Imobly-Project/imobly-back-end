package com.imobly.imobly.domains.users

import com.imobly.imobly.domains.enums.UserRoleEnum

class LandLordDomain(
    id: String? = null,
    firstName: String = "",
    lastName: String = "",
    email: String = "",
    password: String = "",
    telephones: List<String> = emptyList(),
    role: UserRoleEnum = UserRoleEnum.LAND_LORD
): RegisteredUserDomain(id, firstName, lastName, email, telephones, password, role)