package io.lhysin.order.producer

import com.github.sonus21.rqueue.core.RqueueMessageEnqueuer
import io.lhysin.order.dto.CreateOrderForm
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.stream.LongStream

private val logger = KotlinLogging.logger {}

@Component
class OrderProducer (
    private val rqueueMessageEnqueuer : RqueueMessageEnqueuer
        ){

    fun createOrder() {
        val list = LongStream.range(0, 1000)
            .mapToObj { i -> i }
            .toList()

        list.map { i -> CreateOrderForm(itemId = i, userId = i) }
            .chunked(100)
            //.stream()
            .forEach { createOrderForm -> rqueueMessageEnqueuer.enqueue("order-queue", createOrderForm)}
//        list.map { i -> CreateOrderForm(itemId = i*99, userId = i*99) }
//            .chunked(100)
//            .forEach { createOrderForm -> rqueueMessageEnqueuer.enqueue("order-queue", "9999-01-02", createOrderForm)}

        logger.debug("stream.count() : ${list.count()}")

    }
}