package com.exemplo.demo.cucumber

import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

class SaudacaoSteps : En {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<Map<String, Any>>

    init {
        When("realizo uma requisição GET para {string}") { url: String ->
            @Suppress("UNCHECKED_CAST")
            response = restTemplate.getForEntity(url, Map::class.java) as ResponseEntity<Map<String, Any>>
        }
        Then("o status deve ser {int}") { status: Int ->
            assertEquals(status, response.statusCode.value())
        }
        Then("a mensagem deve ser {string}") { mensagem: String ->
            assertEquals(mensagem, response.body?.get("message"))
        }
    }
}
