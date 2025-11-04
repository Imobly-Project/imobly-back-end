package com.imobly.imobly.configuration.security

import com.imobly.imobly.exceptions.AuthenticationFailedException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import com.imobly.imobly.services.security.LandLordUserDetailsService
import com.imobly.imobly.services.security.TenantUserDetailsService
import com.imobly.imobly.services.security.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.hibernate.validator.internal.util.logging.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.jvm.java

@Component
class JwtAuthFilter(
    private val tokenService: TokenService,
    private val landLordUserDetailsService: LandLordUserDetailsService,
    private val tenantUserDetailsService: TenantUserDetailsService
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")
        var authenticated = false
        if (authHeader != null && authHeader.contains("Bearer")) {
            val token = authHeader.substring(7)
            val username = tokenService.extractUsername(token)
            val role = tokenService.extractRole(token)
            val expired = tokenService.isTokenExpired(token)

            if (!expired) {
                val userDetails = when (role) {
                    "LAND_LORD" -> landLordUserDetailsService.loadUserByUsername(username)
                    "TENANT" -> tenantUserDetailsService.loadUserByUsername(username)
                    else -> null
                }
                if (userDetails != null) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    SecurityContextHolder.getContext().authentication = authToken
                    authenticated = true
                }
            }
            if (!authenticated) {
                throw AuthenticationFailedException(RuntimeErrorEnum.ERR0018)
            }
        }
        filterChain.doFilter(request, response)
    }

}