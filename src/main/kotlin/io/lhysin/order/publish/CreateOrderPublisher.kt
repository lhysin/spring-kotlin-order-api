package io.lhysin.order.publish

import io.lhysin.order.dto.CreateOrderForm
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.stream.LongStream

private val logger = KotlinLogging.logger {}

@Component
class CreateOrderPublisher(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun createOrder() {
        val list = LongStream.range(0, 10)
            .mapToObj { i -> i }
            .toList()

        // opsForList
        list.map { i -> CreateOrderForm(itemId = i, userId = i) }
            .forEach { createOrderForm -> redisTemplate.opsForList().leftPush("test-queue", createOrderForm.userId.toString())}


        logger.debug("stream.count() : ${list.count()}")
    }

}