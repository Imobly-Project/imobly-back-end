package com.imobly.imobly.exceptions

import com.imobly.imobly.exceptions.enums.RuntimeErrorEnum

class ResourceNotFoundException(val errorEnum: RuntimeErrorEnum): RuntimeException()