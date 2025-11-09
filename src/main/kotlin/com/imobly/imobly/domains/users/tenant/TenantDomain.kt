package com.imobly.imobly.domains.users.tenant

import com.imobly.imobly.domains.AddressDomain
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.RegisteredUserDomain
import java.time.LocalDate

class TenantDomain(
    id: String? = null,
    firstName: String = "",
    lastName: String = "",
    email: String = "",
    telephones: List<String> = listOf(""),
    password: String = "",
    role: UserRoleEnum = UserRoleEnum.TENANT,
    var rg: String = "",
    var cpf: String = "",
    var birthDate: LocalDate = LocalDate.of(2000, 1, 1),
    var nationality: String = "",
    var maritalStatus: MaritalStatusEnum = MaritalStatusEnum.SINGLE,
    var job: String = "",
    var pathImage: String = "",
    var address: AddressDomain = AddressDomain()
): RegisteredUserDomain(id, firstName, lastName, email, telephones, password, role)