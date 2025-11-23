package com.imobly.imobly.controllers.landlord.mappers

import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.controllers.landlord.dtos.UpdateLandLordDTO
import com.imobly.imobly.controllers.landlord.dtos.UpdateLandLordEmailDTO
import com.imobly.imobly.controllers.tenant.dtos.TelephoneDTO
import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.LandLordDomain
import org.springframework.stereotype.Component

@Component
class LandLordWebMapper {
    fun toDomain(landLord: UpdateLandLordDTO): LandLordDomain =
        LandLordDomain(
            firstName = landLord.firstName?.trim()  ?: "",
            lastName = landLord.lastName?.trim()  ?: "",
            telephones = listOf(
                landLord.telephones?.telephone1?.trim() ?: "",
                landLord.telephones?.telephone2?.trim() ?: "",
                landLord.telephones?.telephone3?.trim() ?: ""
            )
        )

    fun toDomain(landLord: UpdateLandLordEmailDTO): LandLordDomain =
        LandLordDomain(
            email = landLord.email?.trim()  ?: ""
        )

    fun toDomain(landLord: LandLordDTO): LandLordDomain =
        LandLordDomain(
            firstName = landLord.firstName?.trim()  ?: "",
            lastName = landLord.lastName?.trim()  ?: "",
            email = landLord.email?.trim()  ?: "",
            password = landLord.password?.trim() ?: "",
            telephones = listOf(
                landLord.telephones?.telephone1?.trim() ?: "",
                landLord.telephones?.telephone2?.trim() ?: "",
                landLord.telephones?.telephone3?.trim() ?: ""
            ),
            role = landLord.role ?: UserRoleEnum.TENANT
        )

    fun toDTO(landLord: LandLordDomain): LandLordDTO =
        LandLordDTO(
            id = landLord.id,
            firstName = landLord.firstName,
            lastName = landLord.lastName,
            email = landLord.email,
            password = "",
            telephones = TelephoneDTO(
                landLord.telephones.getOrNull(0),
                landLord.telephones.getOrNull(1),
                landLord.telephones.getOrNull(2)
            ),
            role = landLord.role
        )

    fun toDTOs(landLords: List<LandLordDomain>): List<LandLordDTO> =
        landLords.map {
            toDTO(it)
        }
}