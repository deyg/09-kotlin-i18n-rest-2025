package com.exemplo.demo.service

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
open class SaudacaoService(private val messages: MessageSource) {

    @Retryable(value = [RuntimeException::class], maxAttempts = 3, backoff = Backoff(delay = 500))
    open fun obterSaudacao(nome: String?): String {
        if (nome == "erro") {
            throw RuntimeException("Falha simulada")
        }
        val locale = LocaleContextHolder.getLocale()
        return messages.getMessage("saudacao.hello", arrayOf(nome ?: "Dev"), locale)
    }

    @Recover
    open fun recuperar(e: RuntimeException, nome: String?): String {
        val locale = LocaleContextHolder.getLocale()
        return messages.getMessage("saudacao.hello", arrayOf("Dev"), locale)
    }
}
