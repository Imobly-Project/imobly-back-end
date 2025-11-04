package com.imobly.imobly.controllers.authentication

import com.imobly.imobly.controllers.authentication.dtos.AuthDTO
import com.imobly.imobly.controllers.authentication.dtos.TokenDTO
import com.imobly.imobly.controllers.landlord.dtos.LandLordDTO
import com.imobly.imobly.controllers.landlord.mappers.LandLordWebMapper
import com.imobly.imobly.controllers.tenant.dtos.TenantDTO
import com.imobly.imobly.controllers.tenant.mappers.TenantWebMapper
import com.imobly.imobly.domains.enums.UserRoleEnum
import com.imobly.imobly.services.LandLordService
import com.imobly.imobly.services.TenantService
import com.imobly.imobly.services.security.LandLordUserDetailsService
import com.imobly.imobly.services.security.TenantUserDetailsService
import com.imobly.imobly.services.security.TokenService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.Calendar

@RestController
@RequestMapping("/autenticacoes")
class AuthenticationController(
    val tenantService: TenantService,
    val tenantMapper: TenantWebMapper,
    val landLordService: LandLordService,
    val landLordMapper: LandLordWebMapper,
    val landLordUserDetailsService: LandLordUserDetailsService,
    val tenantUserDetailsService: TenantUserDetailsService,
    val tokenService: TokenService
) {

    @PostMapping("/locatario/cadastrar")
    fun signUpTenant(
        @Validated @RequestPart ("tenant") tenant: TenantDTO,
        @RequestPart(value = "file") file: MultipartFile?
    ): ResponseEntity<TenantDTO> = ResponseEntity.status(HttpStatus.CREATED).body(
        tenantMapper.toDTO(tenantService.insert(tenantMapper.toDomain(tenant), file))
    )

    @PostMapping("/locatario/logar")
    fun signInTenant(@Valid @RequestBody auth: AuthDTO): ResponseEntity<TokenDTO> {
        tenantUserDetailsService.loadUserByUsername(auth.email)
        return ResponseEntity.ok(generateToken(auth, UserRoleEnum.TENANT))
    }

    @PostMapping("/locador/cadastrar")
    fun signUpLandLord(@Valid @RequestBody landlord: LandLordDTO): ResponseEntity<LandLordDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            landLordMapper.toDTO(landLordService.insert(landLordMapper.toDomain(landlord)))
        )

    @PostMapping("/locador/logar")
    fun signInLandLord(@Valid @RequestBody auth: AuthDTO):  ResponseEntity<TokenDTO> {
        landLordUserDetailsService.loadUserByUsername(auth.email)
        return ResponseEntity.ok(generateToken(auth, UserRoleEnum.LAND_LORD))
    }

    fun generateToken(auth: AuthDTO, role: UserRoleEnum): TokenDTO {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val date = calendar.time
        val token = tokenService.generateToken(
            subject = auth.email ?: "",
            expiration = date,
            additionalClaims = mapOf(Pair("role", role))
        )
        return TokenDTO(token)
    }
}