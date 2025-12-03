package com.imobly.imobly.persistences.tenant.repositories

import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TenantRepository : JpaRepository<TenantEntity, String> {

    fun findByFirstNameContainingOrLastNameContainingOrCpfContainingAllIgnoreCase(firstName: String, lastName: String, cpf: String): List<TenantEntity>
    fun findByEmail(email: String): Optional<TenantEntity>

    fun existsByEmailAndIdNot(email: String, id: String): Boolean

    fun existsByRgAndIdNot(rg: String, id: String): Boolean

    fun existsByCpfAndIdNot(cpf: String, id: String): Boolean

    fun existsByEmail(email: String): Boolean

}