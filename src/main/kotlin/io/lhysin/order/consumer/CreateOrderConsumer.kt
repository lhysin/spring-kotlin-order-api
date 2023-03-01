package io.lhysin.order.consumer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.dto.MessagePayload
import io.lhysin.order.service.ChaosOrderService
import io.lhysin.order.service.OrderService
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Component
class CreateOrderConsumer(
    private val orderService: OrderService,
    private val chaosOrderService: ChaosOrderService,
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    // pollingInterval
    @Scheduled(fixedDelay = 200)
    fun createOrder() {

        val str = redisTemplate.opsForList().rightPop("test-queue", Duration.ofSeconds(30)) ?: return
        val typeReference = object : TypeReference<MessagePayload<CreateOrderForm>>() {}
        val messagePayload = objectMapper.readValue(str, typeReference)

        try {

            logger.debug(str)

            // prevent duplicate processing
            if (redisTemplate.opsForValue().get("test-queue:processed:${messagePayload.id}") != null) {
                return
            }

            if(messagePayload.value.userId == 3L) {
                chaosOrderService.createOrder(messagePayload.value)
            } else {
                // business logic
                orderService.createOrder(messagePayload.value)
            }

            redisTemplate.opsForValue().set("test-queue:processed:${messagePayload.id}", "true", 60, TimeUnit.SECONDS)
        } catch (e: Exception) {
            logger.error("error : $e")
            if (messagePayload.retryCount < 3) {
                messagePayload.retryCount = messagePayload.retryCount + 1
                redisTemplate.opsForList().leftPush(
                    "test-queue",
                    objectMapper.writeValueAsString(
                        messagePayload
                    )
                )
            } else {
                redisTemplate.opsForList().leftPush(
                    "test-dead-letter-queue",
                    objectMapper.writeValueAsString(
                        messagePayload
                    )
                )
            }

        }

    }
}