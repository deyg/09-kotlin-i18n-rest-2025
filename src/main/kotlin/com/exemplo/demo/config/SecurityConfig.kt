package com.exemplo.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            }
            .httpBasic {}
        return http.build()
    }

    @Bean
    fun users(): UserDetailsService {
        val user = User.withUsername("user").password("{noop}password").roles("USER").build()
        val admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build()
        return InMemoryUserDetailsManager(user, admin)
    }
}
