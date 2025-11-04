package com.imobly.imobly.persistences.tenant.entities

import com.imobly.imobly.domains.enums.MaritalStatusEnum
import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.persistences.property.entities.AddressEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "tb_locatario")
class TenantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,

    @Column(name = "nome", nullable = false, length = 40)
    val firstName: String = "",

    @Column(name = "sobrenome", nullable = false, length = 40)
    val lastName: String = "",

    @Column(nullable = false, length = 100, unique = true)
    val email: String = "",

    @Column(name = "senha", nullable = false, length = 50)
    val  password: String = "",

    @Column(nullable = false, length = 13, unique = true)
    val rg: String,

    @Column(nullable = false, length = 14, unique = true)
    val cpf: String,

    @Column(name = "data_nascimento", nullable = false)
    val birthDate: LocalDate,

    @Column(name = "nacionalidade", nullable = false, length = 50)
    val nationality: String,

    @Column(name = "estado_civil", nullable = false)
    val maritalStatus: MaritalStatusEnum,

    @Column(name = "telefones", nullable = false)
    val telephones: List<String>,

    @Column(name = "caminho_imagem", nullable = false)
    val pathImage: String,

    @Column(name = "trabalho", nullable = false)
    val job: String,

    @Column(name = "papel", nullable = false)
    val role: UserRoleEnum,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "fk_endereco_id", referencedColumnName = "id")
    val address: AddressEntity
)