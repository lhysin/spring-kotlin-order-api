package io.lhysin.order.subscribe

import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration

private val logger = KotlinLogging.logger {}

@Component
class CreateOrderSubscribe(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    // pollingInterval
    @Scheduled(fixedDelay = 1000)
    fun createOrder() {

        var str: String? = null
        try {
            str = redisTemplate.opsForList().rightPop("test-queue", Duration.ofSeconds(60))
            logger.debug(str)
            if(str == "2") {
                throw RuntimeException()
            }
        } catch (e: Exception) {
            if(str != null) {
                redisTemplate.opsForList().leftPush("test-queue", str)
            }

        }

    }
}