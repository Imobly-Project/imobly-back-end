package com.imobly.imobly.exceptions

import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum

class AuthenticationFailedException(val errorEnum: RuntimeErrorEnum): RuntimeException()