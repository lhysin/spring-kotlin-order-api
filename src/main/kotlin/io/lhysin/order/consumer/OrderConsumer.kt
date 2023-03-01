package io.lhysin.order.consumer

import com.github.sonus21.rqueue.annotation.RqueueListener
import com.github.sonus21.rqueue.core.RqueueMessage
import com.github.sonus21.rqueue.listener.RqueueMessageHeaders
import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.entity.Order
import io.lhysin.order.repository.OrderRepository
import io.lhysin.order.type.OrderType
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderConsumer (

    private val orderRepository: OrderRepository
        ) {

    @RqueueListener("order-queue")
    fun createOrder(list: List<CreateOrderForm>,
    @Header(RqueueMessageHeaders.MESSAGE) rqueueMessage : RqueueMessage) {
        list.forEach { dto -> orderRepository.save(Order(userId = dto.userId, itemId = dto.itemId, orderType = OrderType.ORDER)) }
    }

}