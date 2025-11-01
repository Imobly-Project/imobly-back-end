package com.imobly.imobly.services

import com.imobly.imobly.domains.users.LandLordDomain
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import org.springframework.stereotype.Service

@Service
class LandLordService(val repository: LandLordRepository, val mapper: LandLordPersistenceMapper) {
    fun findAll(): List<LandLordDomain> = mapper.toDomains(repository.findAll())

    fun findById(id: String): LandLordDomain =
        mapper.toDomain(repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        }))

    fun insert(landLord: LandLordDomain): LandLordDomain {
        val landLordSaved = repository.save(mapper.toEntity(landLord))
        return mapper.toDomain(landLordSaved)
    }

    fun update(id: String, landLord: LandLordDomain): LandLordDomain {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })
        landLord.id = id
        val landLordUpdated = repository.save(mapper.toEntity(landLord))
        return mapper.toDomain(landLordUpdated)
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow({
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })
        repository.deleteById(id)
    }
}