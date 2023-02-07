package io.lhysin.order.controller

import com.github.sonus21.rqueue.core.RqueueMessageManager
import com.github.sonus21.rqueue.core.support.RqueueMessageUtils
import org.springframework.web.bind.annotation.*

@RestController
class RqueueController(

    private val rqueueMessageManager: RqueueMessageManager

) {
    @GetMapping("/api/v1/rqueue/{queueName}")
    fun findBy(@PathVariable("queueName") queueName: String): List<Any> {
        return rqueueMessageManager.getAllRqueueMessage(queueName)
            .map { rqueueMessage -> RqueueMessageUtils.convertMessageToObject(rqueueMessage, rqueueMessageManager.messageConverter) }
            .toList()
    }

    @GetMapping("/api/v1/rqueue/{queueName}/{id}")
    fun findById(@PathVariable("queueName") queueName: String, @PathVariable("id") id : String): List<Any> {
        return rqueueMessageManager.getAllRqueueMessage(queueName)
            .filter { rqueueMessage -> rqueueMessage.id.equals(id) }
            .map { rqueueMessage -> RqueueMessageUtils.convertMessageToObject(rqueueMessage, rqueueMessageManager.messageConverter) }
            .toList()
    }

}