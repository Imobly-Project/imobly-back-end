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
                    val currentValue = values.getOrDefault(installment.month, 0.0)
                    values[installment.month] = currentValue + installment.monthlyRent
                }
            }
        }
        return values
    }

    fun getChartRentedProperties(): MutableMap<String, Int> {
        val leases = leaseRepository.findAll()

        val values: MutableMap<String, Int> = mutableMapOf()
        values["RENTED"] = 0
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
        values["PAID"]= 0
        values["NOT_PAID"]=0
        payments.forEach {
            it.installments.forEach { installment ->
                val monthActual = LocalDate.now().month
                val yearActual = LocalDate.now().year
                if (installment.month == monthActual && installment.dueDate.year == yearActual && installment.status == PaymentStatusEnum.PAID) {
                    val sum = values["PAID"]?.plus(1)
                    if (sum != null) {
                        values["PAID"] = sum
                    }
                } else if (installment.month == monthActual && installment.dueDate.year == yearActual) {
                    values["NOT_PAID"] = values["NOT_PAID"]!!+1
                }
            }
        }
        return values
    }

}