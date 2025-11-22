package com.imobly.imobly.exceptions

import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum

class OperationNotAllowedException(val errorEnum: RuntimeErrorEnum): RuntimeException()