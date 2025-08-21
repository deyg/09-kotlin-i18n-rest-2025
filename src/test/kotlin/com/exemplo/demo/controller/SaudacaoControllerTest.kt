package com.exemplo.demo.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SaudacaoControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `resposta padrao em ingles quando lang nao informado`() {
        mockMvc.get("/api/saudacao")
            .andExpect {
                status { isOk() }
                jsonPath("$.message") { value("Hello, Dev!") }
            }
    }

    @Test
    fun `resposta em portugues quando lang igual pt`() {
        mockMvc.get("/api/saudacao") { param("lang", "pt") }
            .andExpect {
                status { isOk() }
                jsonPath("$.message") { value("Olá, Dev!") }
            }
    }
}
