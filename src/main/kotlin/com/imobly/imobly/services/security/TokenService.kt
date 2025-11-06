package com.imobly.imobly.services.security

import com.imobly.imobly.exceptions.AuthenticationFailedException
import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Service
class TokenService(@field:Value("\${jwt.secret}") private val secret: String = "") {
    private val signingKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    fun generateToken(subject: String, expiration: Date, additionalClaims: Map<String, Any> = emptyMap()): String =
        Jwts.builder()
            .setClaims(additionalClaims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact()

    fun extractToken(authHeader: String): String =
            if (authHeader.contains("Bearer")) authHeader.substring(7)
            else throw AuthenticationFailedException(RuntimeErrorEnum.ERR0019)

    fun extractUsername(token: String): String = extractAllClaims(token).subject
    fun extractRole(token: String): String = extractAllClaims(token)["role"].toString()

    fun extractId(token: String): String = extractAllClaims(token)["id"].toString()

    fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getClaimFromToken(token, Claims::getExpiration)
        return expiration.before(Date())
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T =
        claimsResolver( extractAllClaims(token) )

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body

}