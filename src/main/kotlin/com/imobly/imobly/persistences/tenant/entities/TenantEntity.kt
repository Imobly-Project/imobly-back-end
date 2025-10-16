package com.imobly.imobly.persistences.tenant.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tb_tenant")
class TenantEntity(
    id: String? = null,
    name: String = "",
    email: String = "",
    password: String = "",
    @Column(name = "rg", nullable = false, length = 50)
    val rg: String,
    @Column(name = "cpf", nullable = false, length = 20, unique = true)
    val cpf: String,
    @Column(name = "data_nascimento", nullable = false)
    val birthDate: String,
    @Column(name = "nacionalidade", nullable = false, length = 50)
    val nationality: String,
    @Column(name = "estado_civil", nullable = false, length = 50)
    val maritalStatus: String
) : UserRegisteredEntity(id = id, name = name, email = email, password = password)