package com.imobly.imobly.services

import com.imobly.imobly.domains.recoverypassword.RecoveryPasswordLandLordDomain
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import com.imobly.imobly.persistences.recoverypassword.mappers.RecoveryPasswordPersistenceMapper
import com.imobly.imobly.persistences.recoverypassword.repositories.RecoveryPasswordLandLordRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PasswordRecoveryService (
    private val landLordRepository: LandLordRepository,
    private val landLordMapper: LandLordPersistenceMapper,
    private val recoveryPasswordRepository: RecoveryPasswordLandLordRepository,
    private val recoveryPasswordMapper: RecoveryPasswordPersistenceMapper,
    private val emailService: EmailService
) {
    fun getCode(email: String) {
        val token =  String.format("%06d", (0..999999).random())
        recoveryPasswordRepository.findByLandLord_Email(email).ifPresentOrElse(
            {
                val recoveryPassword = recoveryPasswordMapper.toDomain(it)
                recoveryPassword.token = token
                recoveryPassword.moment = LocalDateTime.now().plusMinutes(5)
                recoveryPasswordRepository.save(recoveryPasswordMapper.toEntity(recoveryPassword))
            },
            {
                val landlord = landLordRepository.findByEmail(email).orElse(null)
                if (landlord != null) {
                    val recoveryPassword = RecoveryPasswordLandLordDomain(
                        token = token,
                        landLord = landLordMapper.toDomain(landlord),
                        moment = LocalDateTime.now().plusMinutes(5)
                    )
                    recoveryPasswordRepository.save(recoveryPasswordMapper.toEntity(recoveryPassword))
                }
            }
        )
        emailService.sendEmail(email, "Recuperar Senha", "Código de confirmação: $token")
    }

    fun changePassword(email: String, token: String, newPassword: String) {

        if ( !validateToken(email, token) ) {
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0021)
        }

        val landlord = landLordMapper.toDomain(landLordRepository.findByEmail(email).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0020)
        })
        landlord.passwd = BCryptPasswordEncoder().encode(newPassword)
        landLordRepository.save(landLordMapper.toEntity(landlord))

        val recoveryPassword = recoveryPasswordRepository.findByLandLord_Email(email).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0020)
        }
        recoveryPasswordRepository.delete(recoveryPassword)
    }

    fun validateToken(email: String, token: String): Boolean {
        val recoveryPassword = recoveryPasswordRepository.findByLandLord_Email(email).orElse(null) ?: return false

        if (recoveryPassword.moment.isBefore(LocalDateTime.now())) {
            recoveryPasswordRepository.delete(recoveryPassword)
            return false
        }

        val result = recoveryPassword.token == token
        if (!result) {
            recoveryPasswordRepository.delete(recoveryPassword)
        }
        return result
    }
}