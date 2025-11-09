package com.imobly.imobly.domains.users.tenant

import com.imobly.imobly.domains.AddressDomain
import com.imobly.imobly.domains.enums.MaritalStatusEnum
import java.time.LocalDate

class LandLordUpdateTenantDomain(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var telephones: List<String> = listOf(""),
    var rg: String = "",
    var cpf: String = "",
    var birthDate: LocalDate = LocalDate.of(2000, 1, 1),
    var nationality: String = "",
    var maritalStatus: MaritalStatusEnum = MaritalStatusEnum.SINGLE,
    var job: String = "",
    var address: AddressDomain = AddressDomain()
)