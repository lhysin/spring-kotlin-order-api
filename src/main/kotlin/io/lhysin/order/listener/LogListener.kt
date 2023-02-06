package io.lhysin.order.listener

import io.lhysin.order.dto.LogEvent
import io.lhysin.order.service.LogService
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Component
class LogListener (
    private val logService: LogService
) {

    @Async
    @EventListener
    fun log(logEvent: LogEvent) {
        logger.error("!!!! listen EVENT!!!!")
        TimeUnit.NANOSECONDS.sleep(500)
        logService.createLog(logEvent.msg)
    }
}