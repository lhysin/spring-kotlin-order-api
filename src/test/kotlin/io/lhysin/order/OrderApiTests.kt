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

private val logger = KotlinLogging.logger {}

@SpringBootTest(
    classes = [OrderApiApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderApiTests (

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

}
