package com.imobly.imobly.services

import com.imobly.imobly.domains.enums.PaymentStatusEnum
import com.imobly.imobly.persistences.lease.repositories.LeaseRepository
import com.imobly.imobly.persistences.payment.repositories.PaymentRepository
import com.imobly.imobly.persistences.property.repositories.PropertyRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Month
import kotlin.collections.forEach

@Service
class GraphService(
    private val propertiesRepository: PropertyRepository,
    private val leaseRepository: LeaseRepository,
    private val paymentRepository: PaymentRepository
) {

    fun getChartRentByMonth(): MutableMap<Month, Double> {
        val payments = paymentRepository.findAll()
        val values: MutableMap<Month, Double> = mutableMapOf()
        payments.forEach {
            it.installments.forEach { installment ->
                if (installment.status == PaymentStatusEnum.PAID) {
                    val sum = values[installment.month]?.plus(installment.monthlyRent)
                    if (sum != null) {
                        values[installment.month] = sum
                    }
                }
            }
        }
        return values
    }

    fun getChartRentedProperties(): MutableMap<String, Int> {
        val leases = leaseRepository.findAll()
        val values: MutableMap<String, Int> = mutableMapOf()
        leases.forEach {
            if (it.isEnabled) {
                val sum = values["RENTED"]?.plus(1)
                if (sum != null) {
                    values["RENTED"] = sum
                }
            }
        }
        val totalProperties = propertiesRepository.findAll().size
        values["NOT_RENTED"] = totalProperties - (values["RENTED"] ?: 0)
        return values
    }

    fun getChartRentsPaidThisMonth(): MutableMap<String, Int>  {
        val payments = paymentRepository.findAll()
        val values: MutableMap<String, Int> = mutableMapOf()
        payments.forEach {
            it.installments.forEach { installment ->
                val monthActual = LocalDate.now().month
                if (installment.month == monthActual && installment.status == PaymentStatusEnum.PAID) {
                    val sum = values["PAID"]?.plus(1)
                    if (sum != null) {
                        values["PAID"] = sum
                    }
                } else if (installment.month == monthActual) {
                    val sum = values["NOT_PAID"]?.plus(1)
                    if (sum != null) {
                        values["NOT_PAID"] = sum
                    }
                }
            }
        }
        return values
    }

}