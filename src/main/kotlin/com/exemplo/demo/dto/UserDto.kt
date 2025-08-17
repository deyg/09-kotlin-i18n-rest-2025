package com.exemplo.demo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserDto(
    @field:NotBlank(message = "{user.name.required}")
    @field:Size(min = 2, max = 50, message = "{user.name.size}")
    val name: String?,

    @field:NotBlank(message = "{user.email.required}")
    @field:Email(message = "{user.email.invalid}")
    val email: String?
)
