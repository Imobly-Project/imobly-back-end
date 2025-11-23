package com.imobly.imobly.configuration.security

import com.imobly.imobly.exceptions.ResourceNotFoundException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.persistences.landlord.mappers.LandLordPersistenceMapper
import com.imobly.imobly.persistences.landlord.repositories.LandLordRepository
import com.imobly.imobly.persistences.tenant.mappers.TenantPersistenceMapper
import com.imobly.imobly.persistences.tenant.repositories.TenantRepository
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val tokenService: TokenService,
    private val tenantRepository: TenantRepository,
    private val tenantMapper: TenantPersistenceMapper,
    private val landLordRepository: LandLordRepository,
    private val landLordMapper: LandLordPersistenceMapper
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null) {
            val token = tokenService.extractToken(authHeader)
            val username = tokenService.extractUsername(token)
            val role = tokenService.extractRole(token)
            val expired = tokenService.isTokenExpired(token)

            if (!expired) {
                val userDetails = when (role) {
                    "LAND_LORD" -> landLordMapper.toRegisteredUserDomain(landLordRepository.findByEmail(username).orElseThrow {
                        throw BadCredentialsException("Land Lord E-mail not found")
                    })
                    "TENANT" -> tenantMapper.toRegisteredUserDomain(tenantRepository.findByEmail(username).orElseThrow {
                        throw BadCredentialsException("Tenant E-mail not found")
                    })
                    else -> null
                }
                if (userDetails != null) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}