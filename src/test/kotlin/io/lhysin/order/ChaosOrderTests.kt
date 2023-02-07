package io.lhysin.order

import io.lhysin.order.dto.CreateOrderForm

import io.lhysin.order.entity.Log
import io.lhysin.order.entity.Order
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@SpringBootTest(
    classes = [OrderApiApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChaosOrderTests (

    @Autowired
    var restTemplate: TestRestTemplate
) {

    @Test

    fun orderChaosTest() {

        val beforeLogSize = restTemplate.getForEntity("/api/v1/logs", Array<Log>::class.java)
            .body?.size

        val beforeOrderSize = restTemplate.getForEntity("/api/v1/orders", Array<Order>::class.java)
            .body?.size

        logger.debug("beforeLogSize : {}, beforeOrderSize: {}", beforeLogSize, beforeOrderSize)

        assert(beforeLogSize!! < 1)
        assert(beforeOrderSize!! < 1)

        restTemplate.postForObject("/chaos/v1/orders",
            CreateOrderForm(
                userId = 9999,
                itemId = 1234
            ), Void::class.java
        )

        TimeUnit.SECONDS.sleep(3)

        val afterLogSize = restTemplate.getForEntity("/api/v1/logs", Array<Log>::class.java)
            .body?.size

        val afterOrderSize = restTemplate.getForEntity("/api/v1/orders", Array<Order>::class.java)
            .body?.size

        logger.debug("afterLogSize : {}, afterOrderSize: {}", afterLogSize, afterOrderSize)

        assert(afterLogSize!! > 0)
        assert(afterOrderSize!! < 1)

    }

}
