package com.imobly.imobly.services

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.springframework.stereotype.Service
import kotlin.text.toByteArray

@Service
class MqttService {

    private val brokerUrl = "tcp://test.mosquitto.org:1883"
    private val clientId = "backend-kotlin-app"
    private val topic = "imobly/reports"

    private val client = MqttClient(brokerUrl, clientId, MemoryPersistence())

    init {
        client.connect()
        println("MQTT conectado ao broker!")
    }

    fun publish(message: String) {
        val mqttMessage = MqttMessage(message.toByteArray())
        mqttMessage.qos = 1

        client.publish(topic, mqttMessage)

        println("Mensagem MQTT enviada: $message")
    }
}