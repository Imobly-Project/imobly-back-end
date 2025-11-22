package com.imobly.imobly.controllers.passwordrecovery.dtos

data class ResetPasswordDTO(
    val email: String,
    val code:String,
    val newPassword: String
)
