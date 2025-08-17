package com.exemplo.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

@Configuration(proxyBeanMethods = false)
class LocaleConfig : WebMvcConfigurer {

    @Bean
    fun localeResolver(): LocaleResolver =
        SessionLocaleResolver().apply {
            setDefaultLocale(Locale.getDefault())
        }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor =
        LocaleChangeInterceptor().apply { paramName = "lang" }

    @Bean
    fun messageSource() = ResourceBundleMessageSource().apply {
        setBasenames("messages")
        setDefaultEncoding("UTF-8")
        setFallbackToSystemLocale(false)
        setCacheSeconds(3600)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }
}
