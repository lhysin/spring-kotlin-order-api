package io.lhysin.order.dto

class CreateOrderForm(
    val itemId: Long,
    val userId: Long,
) {
    constructor() : this(-1, -1)
}
