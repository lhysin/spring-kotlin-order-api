package io.lhysin.order

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.entity.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(
    classes = [OrderApiApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderApiApplicationTests (

    @Autowired
    var restTemplate: TestRestTemplate
) {

    @Test
    fun contextLoads() {
        val orderId = restTemplate.postForObject("/api/v1/orders",
            CreateOrderForm(
                userId = 9999,
                itemId = 1234
            ), Long::class.java
        )

        assert(orderId != null)

        val order = restTemplate.getForEntity("/api/v1/orders/${orderId}", Order::class.java)

        assert(order != null)


    }

}
