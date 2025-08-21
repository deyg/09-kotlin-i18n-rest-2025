package com.exemplo.demo.controller

import com.exemplo.demo.dto.UserDto
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import com.exemplo.demo.service.SaudacaoService


@RestController
@RequestMapping("/api")
class SaudacaoController(
    private val messages: MessageSource,
    private val saudacaoService: SaudacaoService
) {

    @GetMapping("/saudacao")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    fun saudacao(
        @RequestParam(required = false) nome: String?,
        authentication: Authentication
    ): Map<String, Any> {
        val locale = LocaleContextHolder.getLocale()
        val user = authentication.name
        val hello = messages.getMessage("saudacao.hello", arrayOf(nome ?: user), locale)
        return mapOf("message" to hello, "locale" to locale.toLanguageTag(), "user" to user)
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    fun create(@Validated @RequestBody dto: UserDto): ResponseEntity<Any> {
        val locale = LocaleContextHolder.getLocale()
        val msg = messages.getMessage("user.created", arrayOf(dto.name), locale)
        return ResponseEntity.ok(mapOf("message" to msg))
    }
}
