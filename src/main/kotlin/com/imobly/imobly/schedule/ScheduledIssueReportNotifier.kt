package com.imobly.imobly.services

import com.imobly.imobly.persistences.issuereport.repositories.ReportRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledIssueReportNotifier(
    private val reportRepository: ReportRepository,
    private val mqttService: MqttService
) {
    private var ultimoTotal: Int? = null


    // Executa a cada 5 minutos
    @Scheduled(fixedRate =  5 * 60 * 1000)
    fun verificarNovosReports() {

        val total = reportRepository.count().toInt()

        if (ultimoTotal == null) {
            println("Inicializando contador de reports. Total atual: $total")
            ultimoTotal = total
            return
        }

        val novos = total - (ultimoTotal ?: 0)

        if (novos > 0) {
            val msg = "$novos novos alertas"
            println("Enviando ao MQTT: $msg")
            mqttService.publish(msg)
        } else {
            println("Nenhum novo alerta encontrado.")
        }

        ultimoTotal = total
    }
}
