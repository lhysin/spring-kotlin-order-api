package io.lhysin.order.service

import io.lhysin.order.converter.CreateOrderFormToOrderConverter
import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.dto.CreateOrderRes
import io.lhysin.order.entity.Order
import io.lhysin.order.repository.OrderRepository
import javax.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class OrderService(
    private val createOrderFormToOrderConverter: CreateOrderFormToOrderConverter,
    private val orderRepository: OrderRepository,
    private val logService: LogService
) {
    fun findById(id: Long): Order {
        return orderRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun createOrder(createOrderForm: CreateOrderForm): CreateOrderRes {
        val order: Order = createOrderFormToOrderConverter.convert(createOrderForm);
        orderRepository.save(order)

        val orderId = order.orderId ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        val logId = logService.createLog("Created ORDER!!, orderId : $orderId")

        return CreateOrderRes(
            orderId = orderId,
            logId = logId
        )
    }

    fun findAll(): List<Order> {
        return orderRepository.findAll()
    }
}