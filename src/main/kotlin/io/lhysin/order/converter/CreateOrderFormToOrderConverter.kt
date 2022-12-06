package io.lhysin.order.converter

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.entity.Order
import io.lhysin.order.type.OrderType
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class CreateOrderFormToOrderConverter : Converter<CreateOrderForm, Order> {
    override fun convert(createOrderForm: CreateOrderForm): Order {
        return Order(
            userId = createOrderForm.userId,
            itemId = createOrderForm.itemId,
            orderType = OrderType.ORDER
        );
    }
}