package com.imobly.imobly.exceptions.enums

enum class RuntimeErrorEnum(
    val code: String,
    val message: String
) {
    ERR0001("INVALID_ARGUMENTS", "Há campos invalidos na solicitação"),
    ERR0002("RESOURCE_NOT_FOUND", "O recurso solicitado não foi encontrado"),
    ERR0003("UPLOAD_OBJECT_ERROR", "Houve um erro ao fazer o upload da imagem."),
    ERR0004("UPLOAD_OBJECT_ERROR", "O tipo da imagem enviada não é válida. Escolha entre (JPEG, PNG, WEBP)"),
}