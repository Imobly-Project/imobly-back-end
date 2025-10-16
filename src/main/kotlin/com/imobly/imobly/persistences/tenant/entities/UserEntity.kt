package com.imobly.imobly.persistences.tenant.entities

import jakarta.persistence.*

@Entity
@Table(name = "tb_user")
@Inheritance(strategy = InheritanceType.JOINED)
open class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open val id: String? = null,

    @Column(name = "nome", nullable = false, length = 100)
    open val name: String = "",

    @Column(name = "email", nullable = false, length = 150, unique = true)
    open val email: String = ""
)
