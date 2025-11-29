package com.imobly.imobly.persistences.payment.repositories

import com.imobly.imobly.persistences.payment.entities.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PaymentRepository: JpaRepository<PaymentEntity, String> {
    fun findByLease_Id(id: String): Optional<PaymentEntity>
}