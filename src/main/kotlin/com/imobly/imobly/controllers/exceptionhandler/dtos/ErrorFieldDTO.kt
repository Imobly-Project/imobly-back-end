package com.imobly.imobly.controllers.exceptionhandler.dtos

data class ErrorFieldDTO(
    val name: String,
    val description: String,
    val value: String
)