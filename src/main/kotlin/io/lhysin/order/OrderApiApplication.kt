package io.lhysin.order

import com.github.sonus21.rqueue.spring.EnableRqueue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableRqueue
@EnableAsync
@SpringBootApplication
class OrderApiApplication

fun main(args: Array<String>) {
    runApplication<OrderApiApplication>(*args)
}
