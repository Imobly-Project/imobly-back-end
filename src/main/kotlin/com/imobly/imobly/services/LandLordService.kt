package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.LandLordDomain
import com.imobly.imobly.domains.users.TenantDomain
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class LandLordService(private val repository: LandLordRepository, private val mapper: LandLordPersistenceMapper) {

    fun findById(id: String): LandLordDomain =
        mapper.toDomain(repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })

    fun findByEmail(email: String): LandLordDomain =
        mapper.toDomain(repository.findByEmail(email).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })

    fun createAccount(landLord: LandLordDomain): LandLordDomain {
        checkUniqueFields(landLord)
        landLord.role = UserRoleEnum.LAND_LORD
        landLord.passwd = BCryptPasswordEncoder().encode(landLord.password)
        val landLordSaved = repository.save(mapper.toEntity(landLord))
        return mapper.toDomain(landLordSaved)
    }

    fun updateAccount(id: String, landLord: LandLordDomain): LandLordDomain {
        val landLordFound = mapper.toDomain(repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })

        checkUniqueFields(landLord, id)

        landLordFound.firstName = landLord.firstName
        landLordFound.lastName = landLord.lastName
        landLordFound.email = landLord.email
        landLordFound.telephones = landLord.telephones

        val landLordUpdated = repository.save(mapper.toEntity(landLordFound))
        return mapper.toDomain(landLordUpdated)
    }

    fun deleteAccount(id: String) {
        if (!repository.existsById(id))
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        repository.deleteById(id)
    }

    private fun checkUniqueFields(landLord: LandLordDomain, id: String = "") {
        if (repository.existsByEmailAndIdNot(landLord.email, id))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
    }
}