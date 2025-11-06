package com.imobly.imobly.controllers.graph

import com.imobly.imobly.controllers.graph.dtos.RentByMonthDTO
import com.imobly.imobly.controllers.graph.dtos.RentedPropertiesDTO
import com.imobly.imobly.controllers.graph.dtos.RentsPaidThisMonthDTO
import com.imobly.imobly.services.GraphService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/grafico")
class GraphController(val service: GraphService) {

    @GetMapping("/aluguelpormes")
    fun getChartRentByMonth(): ResponseEntity<RentByMonthDTO> {
        val map = service.getChartRentByMonth()
        return ResponseEntity.ok().body(RentByMonthDTO(map.keys.toList(), map.values.toList()))
    }

    @GetMapping("/propriedadesalugadas")
    fun getChartRentedProperties(): ResponseEntity<RentedPropertiesDTO> {
        val map = service.getChartRentedProperties()
        return ResponseEntity.ok().body(RentedPropertiesDTO(map.keys.toList(), map.values.toList()))
    }

    @GetMapping("/alugueispagosnestemes")
    fun getChartRentsPaidThisMonth(): ResponseEntity<RentsPaidThisMonthDTO> {
        val map = service.getChartRentsPaidThisMonth()
        return ResponseEntity.ok().body(RentsPaidThisMonthDTO(map.keys.toList(), map.values.toList()))
    }

}