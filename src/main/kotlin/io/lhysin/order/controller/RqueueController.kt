package io.lhysin.order.controller

import com.github.sonus21.rqueue.core.RqueueMessageManager
import com.github.sonus21.rqueue.core.support.RqueueMessageUtils
import com.github.sonus21.rqueue.models.db.MessageMetadata
import com.github.sonus21.rqueue.web.service.RqueueMessageMetadataService
import org.springframework.web.bind.annotation.*

@RestController
class RqueueController(

    private val rqueueMessageManager: RqueueMessageManager,
    private val rqueueMessageMetadataService: RqueueMessageMetadataService

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

    @GetMapping("/api/v1/rqueue/{queueName}/status")
    fun findStatusById(@PathVariable("queueName") queueName: String): MessageMetadata? {
        return rqueueMessageMetadataService.get("__rq::m-mdata::${queueName}")
    }

    @GetMapping("/api/v1/rqueue/{queueName}/{id}/status")
    fun findStatusById(@PathVariable("queueName") queueName: String, @PathVariable("id") id : String): MessageMetadata? {
        return rqueueMessageMetadataService.getByMessageId(queueName, id)

    }

    @GetMapping("/api/v1/rqueue/{queueName}/{id}/count")
    fun countById(@PathVariable("queueName") queueName: String, @PathVariable("id") id : String): Int {
        return rqueueMessageManager.getAllRqueueMessage(queueName)
            .filter { rqueueMessage -> rqueueMessage.id.equals(id) }
            .size
    }

}