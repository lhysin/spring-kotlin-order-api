package io.lhysin.order.service

import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.exception.ChaosException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.interceptor.TransactionAspectSupport
import javax.transaction.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class ChaosOrderService (
    private val oderService: OrderService,

    @Autowired
    @Qualifier("transactionStatusSet")
    var transactionStatusSet: MutableSet<TransactionStatus>

) {

    fun createOrder(createOrderForm: CreateOrderForm): Long {
        val createOrderRes = oderService.createOrder(createOrderForm)

        transactionStatusSet.add(TransactionAspectSupport.currentTransactionStatus())
        logger.debug("ChaosOrderService createOrder() transactionStatusSet.first().isCompleted : {}", transactionStatusSet.first().isCompleted)

        if (createOrderRes.orderId < Long.MAX_VALUE) {
            throw ChaosException("Chaos Order Service!!")
        }

        return 1
    }
}