package com.imobly.imobly.services


import com.imobly.imobly.exceptions.InternalErrorException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordRecoveryService (
    private val repository: LandLordRepository,
    private val mapper: LandLordPersistenceMapper,
    private val mailSender: MailSender,
    private val templateMessage: SimpleMailMessage
){

    fun getCode(email: String){

        if(!repository.existsByEmail(email)){
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0020)
        }

        val landlord = mapper.toDomain(repository.findByEmail(email).orElseThrow{
            throw ResourceNotFoundException(
            RuntimeErrorEnum.ERR0020)
        })

        val code =  String.format("%06d", (0..999999).random())

        landlord.recoveryCode = code

        repository.save(mapper.toEntity(landlord))

        val msg = SimpleMailMessage(this.templateMessage)
        msg.subject= "Recuperar Senha"
        msg.setTo(email)
        msg.text = ("Codigo de confirmação: $code")

        try {
            mailSender.send(msg)
        }catch (ex: MailException){
            throw InternalErrorException(RuntimeErrorEnum.ERR0022)
        }

    }

    fun changePassword(email: String, code: String, newPassword: String) {

        val landlord = mapper.toDomain(repository.findByEmail(email).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0020)
        })

        if (code != landlord.recoveryCode) {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0021)
        }

        landlord.recoveryCode = null
        landlord.passwd = BCryptPasswordEncoder().encode(newPassword)

        repository.save(mapper.toEntity(landlord))
    }
}