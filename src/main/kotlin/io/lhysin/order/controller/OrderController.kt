package io.lhysin.order.controller

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.dto.CreateOrderRes
import io.lhysin.order.entity.Order
import io.lhysin.order.publish.CreateOrderPublisher
import io.lhysin.order.publish.OrderPublisher
import io.lhysin.order.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
class OrderController(

    private val orderService: OrderService,
    private val orderPublisher: OrderPublisher,
    private val createOrderPublisher: CreateOrderPublisher,

) {
    @GetMapping("/api/v1/orders/{id}")
    fun findById(@PathVariable("id") id: Long): Order {
        return orderService.findById(id)
    }

    @PostMapping("/api/v1/orders")
    fun createOrder(@RequestBody createOrderForm: CreateOrderForm): CreateOrderRes {
        return orderService.createOrder(createOrderForm)
    }

    @GetMapping("/api/v1/orders")
    fun findAll(): List<Order> {
        return orderService.findAll()
    }

    @GetMapping("/api/v1/orders/publish/rqueue")
    fun publishRqueue() {
        orderPublisher.createOrder()
    }

    @GetMapping("/api/v1/orders/publish/redis")
    fun publishRedis() {
        createOrderPublisher.createOrder()
    }

}