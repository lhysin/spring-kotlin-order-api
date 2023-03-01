package io.lhysin.order.producer

import com.fasterxml.jackson.databind.ObjectMapper
import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.dto.MessagePayload
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.LongStream

private val logger = KotlinLogging.logger {}

@Component
class CreateOrderProducer(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    fun createOrder() {
        val list = LongStream.range(0, 10)
            .mapToObj { i -> i }
            .toList()

        // opsForList
        list.map { i -> CreateOrderForm(itemId = i, userId = i) }
            .forEach { createOrderForm ->
                redisTemplate.opsForList().leftPush(
                    "test-queue",
                    objectMapper.writeValueAsString(
                        MessagePayload(
                            id = UUID.randomUUID().toString(),
                            value = createOrderForm
                        )
                    )
                )
            }


        logger.debug("stream.count() : ${list.count()}")
    }

}