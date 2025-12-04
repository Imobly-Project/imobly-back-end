package com.imobly.imobly.persistences.appointment.entities

import com.imobly.imobly.persistences.property.entities.PropertyEntity
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
@Table(name = "tb_agendamento")
class AppointmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,

    @Column(name = "nome_convidado", nullable = false, length = 50)
    val guestName: String,

    @Column(name = "data_horario", nullable = false)
    val moment: LocalDateTime,

    @Column(name = "telefone", nullable = false, length = 20)
    val telephone: String,

    @ManyToOne
    @JoinColumn(name = "fk_propriedade_id", referencedColumnName = "id")
    val property: PropertyEntity
)