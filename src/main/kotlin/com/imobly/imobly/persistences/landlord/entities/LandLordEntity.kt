package com.imobly.imobly.persistences.landlord.entities

import com.imobly.imobly.domains.enums.UserRoleEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_locador")
class LandLordEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,

    @Column(name = "nome", nullable = false, length = 40)
    val firstName: String = "",

    @Column(name = "sobrenome", nullable = false, length = 40)
    val lastName: String = "",

    @Column(nullable = false, length = 100, unique = true)
    val email: String = "",

    @Column(name = "telefones", nullable = false)
    val telephones: List<String>,

    @Column(name = "papel", nullable = false)
    val role: UserRoleEnum,

    @Column(name = "senha", nullable = false, length = 700)
    val  password: String = ""
)