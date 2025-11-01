package com.imobly.imobly.persistences.lease.entities

import com.imobly.imobly.persistences.property.entities.PropertyEntity
import com.imobly.imobly.persistences.tenant.entities.TenantEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tb_locacao")
class LeaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    @Column(name = "data_inicio", nullable = false)
    val startDate: LocalDate,
    @Column(name = "data_termino", nullable = false)
    val endDate: LocalDate,
    @Column(name = "criado_em", nullable = false)
    val createdAt: LocalDateTime,
    @Column(name = "última_vez_atualizado_em", nullable = false)
    val lastUpdatedAt: LocalDateTime,
    @OneToOne
    @JoinColumn(name = "fk_propriedade_id", referencedColumnName = "id")
    val property: PropertyEntity,
    @ManyToOne
    @JoinColumn(name = "fk_locatario_id", referencedColumnName = "id")
    val tenant: TenantEntity,
    @Column(name = "valor_aluguel", nullable = false)
    val monthlyRent: Double,
    @Column(name = "deposito_caucao", nullable = false)
    val securityDeposit: Double,
    @Column(name = "data_pagamento", nullable = false)
    val paymentDueDay: Int,
    @Column(name = "esta_ativo", nullable = false)
    val isEnabled: Boolean
)