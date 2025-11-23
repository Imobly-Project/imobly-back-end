package com.imobly.imobly.persistences.changeemail.entities

import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tb_troca_email_locatario")
class ChangeEmailTenantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,

    @OneToOne
    @JoinColumn(name = "fk_locatario_id", referencedColumnName = "id")
    val tenant: TenantEntity,

    @Column(nullable = false, length = 100)
    val email: String,

    @Column(name = "codigo_validacao", nullable = false, length = 6)
    val token: String,

    @Column(name = "momento", nullable = false)
    val moment: LocalDateTime
)