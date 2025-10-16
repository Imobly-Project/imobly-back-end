package com.imobly.imobly.persistences.tenant.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tb_user_registered")
open class UserRegisteredEntity(
    id: String? = null,
    name: String = "",
    email: String = "",
    @Column(name = "senha", nullable = false)
    open val password: String = ""
) : UserEntity(id = id, name = name, email = email)