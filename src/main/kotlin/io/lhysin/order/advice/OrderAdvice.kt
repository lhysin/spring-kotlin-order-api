package io.lhysin.order.advice

import io.lhysin.order.dto.LogEvent
import io.lhysin.order.exception.ChaosException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.function.ServerResponse

private val logger = KotlinLogging.logger {}

@ControllerAdvice(basePackages = ["io.lhysin.order"])
class OrderAdvice (
    private val applicationEventPublisher : ApplicationEventPublisher,

    @Autowired
    @Qualifier("transactionStatusSet")
    var transactionStatusSet: MutableSet<TransactionStatus>
) {

    @ExceptionHandler(ChaosException::class)
    fun handleChaosException(ex: ChaosException): ServerResponse {
        logger.debug("OrderAdvice handleChaosException() transactionStatusSet.first().isCompleted : {}", transactionStatusSet.first().isCompleted)

        applicationEventPublisher.publishEvent(LogEvent(msg=ex.message?:ex.toString()))
        logger.error("!!!! publish EVENT!!!!")

        logger.debug("ChaosException : {}", ex.stackTrace)

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build()
    }
}