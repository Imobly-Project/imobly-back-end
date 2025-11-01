package com.imobly.imobly.persistences.payment.repositories

import com.imobly.imobly.persistences.payment.entities.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository: JpaRepository<PaymentEntity, String>