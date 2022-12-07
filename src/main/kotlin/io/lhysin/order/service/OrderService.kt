package io.lhysin.order.service

import io.lhysin.order.converter.CreateOrderFormToOrderConverter
import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.entity.Order
import io.lhysin.order.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class OrderService(
    private val createOrderFormToOrderConverter: CreateOrderFormToOrderConverter,
    private val orderRepository: OrderRepository
) {
    fun findById(id: Long): Order {
        return orderRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    }

    fun createOrder(createOrderForm: CreateOrderForm): Long {
        val order: Order = createOrderFormToOrderConverter.convert(createOrderForm);
        orderRepository.save(order);

        return order.orderId
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}