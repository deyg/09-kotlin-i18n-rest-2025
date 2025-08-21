package com.exemplo.demo.controller

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import com.exemplo.demo.dto.UserDto
import com.exemplo.demo.service.SaudacaoService

@RestController
@RequestMapping("/api")
class SaudacaoController(
    private val messages: MessageSource,
    private val saudacaoService: SaudacaoService
) {

    @GetMapping("/saudacao")
    fun saudacao(@RequestParam(required = false) nome: String?): Map<String, Any> {
        val locale = LocaleContextHolder.getLocale()
        val hello = saudacaoService.obterSaudacao(nome)
        return mapOf("message" to hello, "locale" to locale.toLanguageTag())
    }

    @PostMapping("/users")
    fun create(@Validated @RequestBody dto: UserDto): ResponseEntity<Any> {
        val locale = LocaleContextHolder.getLocale()
        val msg = messages.getMessage("user.created", arrayOf(dto.name), locale)
        return ResponseEntity.ok(mapOf("message" to msg))
    }
}
