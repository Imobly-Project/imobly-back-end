package com.imobly.imobly.exceptions.enums

enum class RuntimeErrorEnum(
    val code: String,
    val message: String
) {
    ERR0001("INVALID_ARGUMENTS", "Há campos invalidos na solicitação"),
    ERR0002("RESOURCE_NOT_FOUND", "O recurso solicitado não foi encontrado"),
    ERR0003("UPLOAD_OBJECT_ERROR", "Houve um erro inesperado ao fazer o upload da imagem."),
    ERR0004("UPLOAD_OBJECT_ERROR", "O tipo da imagem não é suportada. Escolha entre (JPEG, PNG, WEBP)"),
    ERR0005("DUPLICATE_RESOURCE", "Este E-mail já está registrado"),
    ERR0006("DUPLICATE_RESOURCE", "Este CPF já está registrado"),
    ERR0007("DUPLICATE_RESOURCE", "Este RG já está registrado"),
    ERR0008("UPLOAD_OBJECT_ERROR", "Não foi possível obter a extensão do arquivo"),
    ERR0009("MULTIPARTFILE_ERROR", "A(s) imagem(ns) é(são) obrigatória(s)"),
    ERR0010("MULTIPARTFILE_ERROR", "O número de imagens deve estar entre 3 e 15"),

}