package com.imobly.imobly.persistences.property.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "tb_endereco")
class AddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    @Column(nullable = false, length = 9)
    val cep: String,
    @Column(name = "estado", nullable = false, length = 20)
    val state: String,
    @Column(name = "cidade", nullable = false, length = 50)
    val city: String,
    @Column(name = "bairro", nullable = false, length = 60)
    val neighborhood: String,
    @Column(name = "rua", nullable = false, length = 60)
    val street: String,
    @Column(name = "numero", nullable = false, length = 5)
    val number: String,
    @Column(name = "complemento", length = 20)
    val complement: String
)