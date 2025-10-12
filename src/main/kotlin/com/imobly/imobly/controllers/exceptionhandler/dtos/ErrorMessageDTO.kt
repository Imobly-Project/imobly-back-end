package com.imobly.imobly.controllers.exceptionhandler.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class ErrorMessageDTO(
    val code: String,
    val status: Int,
    val message: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT-3")
    val timestamp: Instant,
    val path: String,
    val errorFields: List<ErrorFieldDTO>? = null
)