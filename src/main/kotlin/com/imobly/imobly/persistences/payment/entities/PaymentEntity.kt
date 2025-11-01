package com.imobly.imobly.persistences.payment.entities

import com.imobly.imobly.persistences.lease.entities.LeaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tb_pagamento")
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    @OneToOne
    val lease: LeaseEntity,
    @OneToMany(cascade = [CascadeType.ALL])
    val installments: List<MonthlyInstallmentEntity>
)