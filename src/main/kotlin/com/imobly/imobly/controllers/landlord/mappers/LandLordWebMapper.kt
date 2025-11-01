package com.imobly.imobly.controllers.landlord.mappers

import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.controllers.tenant.dtos.TelephoneDTO
import com.imobly.imobly.domains.users.LandLordDomain
import org.springframework.stereotype.Component

@Component
class LandLordWebMapper {
    fun toDomain(landLord: LandLordDTO): LandLordDomain =
        LandLordDomain(
            firstName = (landLord.firstName ?: "").trim(),
            lastName = (landLord.lastName ?: "").trim(),
            email = (landLord.email ?: "").trim(),
            password = (landLord.password ?: "").trim(),
            telephones = landLord.telephones?.map { it.telephone } ?: emptyList(),
        )

    fun toDTO(landLord: LandLordDomain): LandLordDTO =
        LandLordDTO(
            id = landLord.id,
            firstName = landLord.firstName,
            lastName = landLord.lastName,
            email = landLord.email,
            password = landLord.password,
            telephones = landLord.telephones.map { TelephoneDTO(it) },
        )

    fun toDTOs(landLords: List<LandLordDomain>): List<LandLordDTO> =
        landLords.map {
            toDTO(it)
        }
}