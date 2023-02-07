package io.lhysin.order.subscribe

import com.github.sonus21.rqueue.annotation.RqueueListener
import com.github.sonus21.rqueue.core.RqueueMessage
import com.github.sonus21.rqueue.listener.RqueueMessageHeaders
import io.lhysin.order.dto.CreateOrderForm
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Component
class OrderSubscribe {

    @RqueueListener("order-queue")
    fun createOrder(createOrderForm: CreateOrderForm,
    @Header(RqueueMessageHeaders.MESSAGE) rqueueMessage : RqueueMessage) {
        logger.debug("OrderSubscribe.createOrder() rqueueMessage.failureCount : ${rqueueMessage.failureCount}")
        TimeUnit.SECONDS.sleep(10)
        logger.debug("OrderSubscribe.createOrder() createOrderForm.userId : ${createOrderForm}")
    }
}