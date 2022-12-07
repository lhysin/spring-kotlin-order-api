package io.lhysin.order.controller

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.entity.Order
import io.lhysin.order.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(

    private val orderService: OrderService

) {
    @GetMapping("/api/v1/orders/{id}")
    fun findById(@PathVariable("id") id: Long): Order {
        return orderService.findById(id);
    }

    @PostMapping("/api/v1/orders")
    fun createOrder(@RequestBody createOrderForm: CreateOrderForm): Long {
        return orderService.createOrder(createOrderForm);
    }
}