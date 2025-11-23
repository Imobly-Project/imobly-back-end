package com.imobly.imobly.services

import com.imobly.imobly.exceptions.InternalErrorException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: MailSender,
    private val templateMessage: SimpleMailMessage
) {

    fun sendEmail(to: String, subject: String, content: String) {
        val message = SimpleMailMessage(templateMessage)

        message.setTo(to)
        message.subject = subject
        message.text = content

        try {
            mailSender.send(message)
        } catch (ex: MailException) {
            throw InternalErrorException(RuntimeErrorEnum.ERR0022)
        }
    }
}