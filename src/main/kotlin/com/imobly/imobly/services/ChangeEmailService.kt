package com.imobly.imobly.services

import com.imobly.imobly.domains.changeemail.ChangeEmailLandLordDomain
import com.imobly.imobly.domains.users.LandLordDomain
import com.imobly.imobly.exceptions.DuplicateResourceException
import com.imobly.imobly.exceptions.OperationNotAllowedException
import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.changeemail.mappers.ChangeEmailPersistenceMapper
import com.imobly.imobly.persistences.changeemail.repositories.ChangeEmailLandLordRepository
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChangeEmailService(
    private val landLordRepository: LandLordRepository,
    private val landLordMapper: LandLordPersistenceMapper,
    private val changeEmailRepository: ChangeEmailLandLordRepository,
    private val changeEmailMapper: ChangeEmailPersistenceMapper,
    private val service: EmailService
) {

    fun sendCodeForUpdateEmail(id: String, landLord: LandLordDomain) {
        val token =  String.format("%06d", (0..999999).random())
        changeEmailRepository.findByLandLord_Id(id).ifPresentOrElse(
            {
                val changeEmail = changeEmailMapper.toDomain(it)
                changeEmail.token = token
                changeEmail.email = landLord.email
                changeEmail.moment = LocalDateTime.now().plusMinutes(10)
                checkIfEmailAlreadyExists(landLord.email)
                changeEmailRepository.save(changeEmailMapper.toEntity(changeEmail))
            },
            {
                val landLordFound = landLordRepository.findById(id).orElseThrow {
                    throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
                }
                val changeEmail = ChangeEmailLandLordDomain(
                    token = token,
                    landLord = landLordMapper.toDomain(landLordFound),
                    email = landLordFound.email,
                    moment = LocalDateTime.now().plusMinutes(10)
                )
                checkIfEmailAlreadyExists(landLord.email)
                changeEmailRepository.save(changeEmailMapper.toEntity(changeEmail))
            }
        )

        service.sendEmail(landLord.email, "Troca de E-mail", "Codigo de confirmação: $token")
    }

    fun updateEmail(id: String, code: String) {
        if ( !validateToken(id, code) ) {
            throw OperationNotAllowedException(RuntimeErrorEnum.ERR0021)
        }
        val landLordFound = landLordMapper.toDomain(landLordRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        })
        val changeEmail = changeEmailRepository.findByLandLord_Id(id).orElseThrow {
            throw ResourceNotFoundException(RuntimeErrorEnum.ERR0013)
        }
        landLordFound.email = changeEmail.email
        changeEmailRepository.delete(changeEmail)
        checkIfEmailAlreadyExists(changeEmail.email)
        landLordRepository.save(landLordMapper.toEntity(landLordFound))
    }

    private fun validateToken(id: String, token: String): Boolean {
        val changeEmail = changeEmailRepository.findByLandLord_Id(id).orElse(null) ?: return false

        if (changeEmail.moment.isBefore(LocalDateTime.now())) {
            changeEmailRepository.delete(changeEmail)
            return false
        }

        if (changeEmail.token != token) {
            changeEmailRepository.delete(changeEmail)
            return false
        }
        return true
    }

    private fun checkIfEmailAlreadyExists(email: String) {
        if (landLordRepository.existsByEmail(email))
            throw DuplicateResourceException(RuntimeErrorEnum.ERR0005)
    }
}