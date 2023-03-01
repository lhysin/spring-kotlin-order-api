package io.lhysin.order.dto

class MessagePayload<Any> (
    val id: String,
    val value: Any,
    var retryCount : Int = 0,
)
