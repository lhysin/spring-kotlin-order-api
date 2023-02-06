package io.lhysin.order.controller

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.exception.ChaosException
import io.lhysin.order.service.ChaosOrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChaosOrderController(

    private val chaosOrderService: ChaosOrderService,

) {

    @PostMapping("/chaos/v1/orders")
    fun createOrder(@RequestBody createOrderForm: CreateOrderForm): Long {
        return chaosOrderService.createOrder(createOrderForm)
    }
}