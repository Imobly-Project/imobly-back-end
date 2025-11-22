package com.imobly.imobly.configuration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(val jwtAuthFilter: JwtAuthFilter) {

    @Bean
    fun getEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .sessionManagement {
                it.disable()
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/autenticacoes/**").permitAll()

                    .requestMatchers("/redefinirsenha/**").permitAll()

                    .requestMatchers("/locadores/**").hasRole("LAND_LORD")

                    .requestMatchers("/graficos/**").hasRole("LAND_LORD")

                    .requestMatchers("/categorias/encontrartodos", "/categorias/encontrarporid/**").permitAll()
                    .requestMatchers("/categorias/inserir", "/categorias/atualizar/**", "/categorias/deletar/**").hasRole("LAND_LORD")

                    .requestMatchers(  "/reportacoes/encontrarporperfil", "/reportacoes/criar").hasRole("TENANT")
                    .requestMatchers("/reportacoes/encontrartodos", "/reportacoes/responderreportacao/**", "/reportacoes/atualizarstatus/**", "/reportacoes/deletar/**").hasRole("LAND_LORD")

                    .requestMatchers("/propriedades/encontrartodos", "/propriedades/encontrarporid/**").permitAll()
                    .requestMatchers("/propriedades/inserir", "/propriedades/atualizar/**", "/propriedades/deletar/**").hasRole("LAND_LORD")

                    .requestMatchers("/locacoes/encontrartodos", "/locacoes/encontrarporid/**").hasRole("TENANT")
                    .requestMatchers("/locacoes/inserir", "/locacoes/atualizar/**", "/locacoes/alternarativo/**").hasRole("LAND_LORD")

                    .requestMatchers("/locatarios/encontrarperfil", "/locatarios/atualizarperfil", "/locatarios/deletarperfil").hasRole("TENANT")
                    .requestMatchers("/locatarios/encontrartodos", "/locatarios/atualizar/**", "/locatarios/deletar/**").hasRole("LAND_LORD")

                    .requestMatchers("/pagamentos/encontrartodos", "/pagamentos/encontrarporid/**").hasRole("TENANT")
                    .requestMatchers("/pagamentos/atualizarstatus/**").hasRole("LAND_LORD")

                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}