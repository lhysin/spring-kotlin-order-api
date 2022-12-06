package io.lhysin.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinOrderApiApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinOrderApiApplication>(*args)
}
