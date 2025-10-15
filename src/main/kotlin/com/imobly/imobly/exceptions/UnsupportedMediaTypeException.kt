package com.imobly.imobly.exceptions

import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum

class UnsupportedMediaTypeException(val errorEnum: RuntimeErrorEnum): RuntimeException()