package com.imobly.imobly.persistences.property.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tb_propriedade")
class PropertyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    @Column(name = "titulo", nullable = false, length = 50)
    val title: String,
    @Column(name = "caminho_imagens", nullable = false)
    val pathImages: List<String>,
    @Column(name = "descricao", nullable = false, length = 1500)
    val description: String,
    @Column(name = "aluguel", nullable = false)
    val rentalValue: Double,
    @Column(nullable = false)
    val area: Float,
    @Column(name = "quartos", nullable = false)
    val bedrooms: Int,
    @Column(name = "banheiros", nullable = false)
    val bathrooms: Int,
    @Column(name = "espacos_garagem", nullable = false)
    val garageSpaces: Int,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "fk_endereco_id", referencedColumnName = "id")
    val address: AddressEntity
)