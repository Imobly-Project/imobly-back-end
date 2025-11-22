package com.imobly.imobly.controllers.authentication

import com.imobly.imobly.controllers.authentication.dtos.AuthDTO
import com.imobly.imobly.controllers.authentication.dtos.TokenDTO
import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.controllers.landlord.mappers.LandLordWebMapper
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.domains.users.RegisteredUserDomain
import com.imobly.imobly.services.LandLordService
import com.imobly.imobly.services.TenantService
import com.imobly.imobly.services.security.TokenService
import com.imobly.imobly.services.security.UserDetailsService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/autenticacoes")
class AuthenticationController(
    val tenantService: TenantService,
    val tenantMapper: TenantWebMapper,
    val landLordService: LandLordService,
    val landLordMapper: LandLordWebMapper,
    val authenticationManager: AuthenticationManager,
    val userDetailsService: UserDetailsService,
    val tokenService: TokenService
) {

    @PostMapping("/locatario/cadastrar")
    fun signUpTenant(
        @Validated @RequestPart ("tenant") tenant: TenantDTO,
        @RequestPart(value = "file") file: MultipartFile?
    ): ResponseEntity<TenantDTO> = ResponseEntity.status(HttpStatus.CREATED).body(
        tenantMapper.toDTO(tenantService.createAccount(tenantMapper.toDomain(tenant), file))
    )

    @PostMapping("/locatario/logar")
    fun signInTenant(@Valid @RequestBody auth: AuthDTO): ResponseEntity<TokenDTO> {
        userDetailsService.role = UserRoleEnum.TENANT
        val authenticate = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(auth.email, auth.password)
        )
        val user = authenticate.principal as RegisteredUserDomain
        return ResponseEntity.ok(generateToken(user.email, user.role, user.id ?: ""))
    }

    @PostMapping("/locador/cadastrar")
    fun signUpLandLord(@Valid @RequestBody landlord: LandLordDTO): ResponseEntity<LandLordDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            landLordMapper.toDTO(landLordService.createAccount(landLordMapper.toDomain(landlord)))
        )

    @PostMapping("/locador/logar")
    fun signInLandLord(@Valid @RequestBody auth: AuthDTO):  ResponseEntity<TokenDTO> {
        userDetailsService.role = UserRoleEnum.LAND_LORD
        val authenticate = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(auth.email, auth.password)
        )
        val user = authenticate.principal as RegisteredUserDomain
        return ResponseEntity.ok(generateToken(user.email, user.role, user.id ?: ""))
    }

    private fun generateToken(email: String, role: UserRoleEnum, id: String): TokenDTO {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val date = calendar.time
        val token = tokenService.generateToken(
            subject = email,
            expiration = date,
            additionalClaims = mapOf(Pair("role", role), Pair("id", id))
        )
        return TokenDTO(token)
    }
}