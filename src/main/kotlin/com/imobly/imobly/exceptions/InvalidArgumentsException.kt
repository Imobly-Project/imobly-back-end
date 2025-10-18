package com.imobly.imobly.exceptions

import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum

class InvalidArgumentsException(val errorEnum: RuntimeErrorEnum): RuntimeException()