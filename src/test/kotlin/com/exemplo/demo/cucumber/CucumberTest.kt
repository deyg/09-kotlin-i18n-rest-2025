package com.exemplo.demo.cucumber

import io.cucumber.junit.platform.engine.Cucumber
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest

@Cucumber
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
class CucumberTest
