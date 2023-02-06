package io.lhysin.order.service

import io.lhysin.order.advice.logger
import io.lhysin.order.dto.CreateOrderForm
import io.lhysin.order.exception.ChaosException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import javax.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.interceptor.TransactionAspectSupport

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