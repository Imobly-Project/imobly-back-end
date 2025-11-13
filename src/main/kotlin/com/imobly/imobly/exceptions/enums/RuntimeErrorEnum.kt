package com.imobly.imobly.exceptions.enums

enum class RuntimeErrorEnum(val code: String, val message: String) {
    ERR0001("INVALID_ARGUMENTS", "Há campos inválidos na solicitação"),
    ERR0002("INVALID_ARGUMENTS", "A data de início não pode ser posterior a data de términio"),
    ERR0003("UPLOAD_OBJECT_ERROR", "Houve um erro inesperado ao fazer o upload da imagem."),
    ERR0004("UPLOAD_OBJECT_ERROR", "O tipo da imagem não é suportada. Escolha entre (JPEG, PNG, WEBP)"),
    ERR0005("DUPLICATE_RESOURCE", "Este E-mail já está registrado"),
    ERR0006("DUPLICATE_RESOURCE", "Este CPF já está registrado"),
    ERR0007("DUPLICATE_RESOURCE", "Este RG já está registrado"),
    ERR0008("UPLOAD_OBJECT_ERROR", "Não foi possível obter a extensão do arquivo"),
    ERR0009("MULTIPARTFILE_ERROR", "A(s) imagem(ns) é(são) obrigatória(s)"),
    ERR0010("MULTIPARTFILE_ERROR", "O número de imagens deve estar entre 3 e 15"),
    ERR0011("RESOURCE_NOT_FOUND", "A propriedade solicitada não foi encontrada"),
    ERR0012("RESOURCE_NOT_FOUND", "O locatário solicitado não foi encontrado"),
    ERR0013("RESOURCE_NOT_FOUND", "O locador solicitado não foi encontrado"),
    ERR0014("RESOURCE_NOT_FOUND", "A categoria solicitada não foi encontrada"),
    ERR0015("RESOURCE_NOT_FOUND", "A reportação solicitada não foi encontrada"),
    ERR0016("RESOURCE_NOT_FOUND", "A locação solicitada não foi encontrada"),
    ERR0017("RESOURCE_NOT_FOUND", "O pagamento solicitado não foi encontrado"),
    ERR0018("AUTHENTICATION_FAILED", "Token não encontrado")
}
