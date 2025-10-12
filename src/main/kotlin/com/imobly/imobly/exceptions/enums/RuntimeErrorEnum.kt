package com.imobly.imobly.exceptions.enums

enum class RuntimeErrorEnum(
    val code: String,
    val message: String
) {
    ERR0001("INVALID_ARGUMENTS", "Há campos invalidos na solicitação"),
    ERR0002("RESOURCE_NOT_FOUND", "O recurso solicitado não foi encontrado"),
}