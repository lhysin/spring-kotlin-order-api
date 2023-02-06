package io.lhysin.order

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.dto.CreateOrderRes
import io.lhysin.order.entity.Log
import io.lhysin.order.entity.Order
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import java.lang.reflect.TypeVariable
import java.util.concurrent.TimeUnit

val logger = KotlinLogging.logger {}

@SpringBootTest(
    classes = [OrderApiApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderApiApplicationTests (

    @Autowired
    var restTemplate: TestRestTemplate
) {

    @Test
    fun orderApiTest() {
        val createOrderRes = restTemplate.postForObject("/api/v1/orders",
            CreateOrderForm(
                userId = 9999,
                itemId = 1234
            ), CreateOrderRes::class.java
        )

        val order = restTemplate.getForEntity("/api/v1/orders/${createOrderRes.orderId}", Order::class.java).body
        assert(order?.orderId != null)

        val log = restTemplate.getForEntity("/api/v1/logs/${createOrderRes.logId}", Log::class.java).body
        val msg = log?.msg
        assert(msg != null)

        logger.debug("Crated Order Log Message : {}", msg)

    }

    @Test
    fun orderChaosTest() {

        val beforeLogSize = restTemplate.getForEntity("/api/v1/logs", Array<Log>::class.java)
            .body?.size

        val beforeOrderSize = restTemplate.getForEntity("/api/v1/orders", Array<Order>::class.java)
            .body?.size

        logger.debug("beforeLogSize : {}, beforeOrderSize: {}", beforeLogSize, beforeOrderSize)

        assert(beforeLogSize!! < 1)
        assert(beforeOrderSize!! < 1)

        val void = restTemplate.postForObject("/chaos/v1/orders",
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
