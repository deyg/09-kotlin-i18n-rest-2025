package com.exemplo.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication(proxyBeanMethods = false)
class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Application>(*args)
        }
    }
}
