package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.landlord.LandLordDomain
import com.imobly.imobly.domains.users.tenant.TenantDomain
import com.imobly.imobly.domains.users.landlord.UpdateLandLordDomain
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

    fun insert(landLord: LandLordDomain): LandLordDomain {
        checkUniqueFields(landLord)
        landLord.role = UserRoleEnum.LAND_LORD
        landLord.passwd = BCryptPasswordEncoder().encode(landLord.password)
        val landLordSaved = repository.save(mapper.toEntity(landLord))
        return mapper.toDomain(landLordSaved)
    }

    fun update(id: String, landLord: UpdateLandLordDomain): LandLordDomain {
        val landLordInDatabase = mapper.toDomain(repository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })

        checkUniqueFields(landLord, id)

        landLordInDatabase.firstName = landLord.firstName
        landLordInDatabase.lastName = landLord.lastName
        landLordInDatabase.email = landLord.email
        landLordInDatabase.telephones = landLord.telephones

        val landLordUpdated = repository.save(mapper.toEntity(landLordInDatabase))
        return mapper.toDomain(landLordUpdated)
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })
        repository.deleteById(id)
    }

    private fun checkUniqueFields(landLord: LandLordDomain, id: String = "") {
        repository.findByEmail(landLord.email).ifPresent {
            if (it.email == landLord.email && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
        }
    }

    private fun checkUniqueFields(landLord: UpdateLandLordDomain, id: String = "") {
        repository.findByEmail(landLord.email).ifPresent {
            if (it.email == landLord.email && it.id != id)
                throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
        }
    }
}