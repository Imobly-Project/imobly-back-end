package com.imobly.imobly.controllers.graph.dtos

import java.time.Month

data class RentByMonthDTO(
    val x: List<Month>,
    val y: List<Double>
)