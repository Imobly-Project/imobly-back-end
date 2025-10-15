package com.imobly.imobly.exceptions

import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum

class InternalErrorException(val errorEnum: RuntimeErrorEnum): RuntimeException()