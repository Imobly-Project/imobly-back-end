package com.imobly.imobly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ImoblyApplication

fun main(args: Array<String>) {
	runApplication<ImoblyApplication>(*args)
}
