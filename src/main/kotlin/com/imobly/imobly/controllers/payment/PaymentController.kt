package com.imobly.imobly.controllers.payment

import com.imobly.imobly.controllers.payment.dtos.PaymentDTO
import com.imobly.imobly.controllers.payment.dtos.StatusInstallmentDTO
import com.imobly.imobly.controllers.payment.mappers.PaymentWebMapper
import com.imobly.imobly.controllers.payment.mappers.StatusInstallmentWebMapper
import com.imobly.imobly.services.PaymentService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pagamentos")
class PaymentController(
    val service: PaymentService,
    val paymentMapper: PaymentWebMapper,
    val installmentMapper: StatusInstallmentWebMapper
) {
    @GetMapping("/encontrartodos")
    fun findAll(): ResponseEntity<List<PaymentDTO>> = ResponseEntity.ok().body(
        paymentMapper.toDTOs(service.findAll())
    )

    @GetMapping("/encontrarporid/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<PaymentDTO> =
        ResponseEntity.ok().body(
            paymentMapper.toDTO(service.findById(id))
        )

    @PatchMapping("/atualizarstatus/{idPayment}/{idInstallment}")
    fun updateStatusInstallment(
        @PathVariable idPayment: String,
        @PathVariable idInstallment: String,
        @Valid @RequestBody statusInstallment: StatusInstallmentDTO
    ): ResponseEntity<Void> {
        service.updateStatusInstallment(
            idPayment,
            idInstallment,
            installmentMapper.toDomain(statusInstallment)
        )
        return ResponseEntity.ok().build()
    }
}