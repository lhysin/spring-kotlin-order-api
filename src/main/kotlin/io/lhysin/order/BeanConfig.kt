package io.lhysin.order

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.TransactionStatus

@Configuration
class BeanConfig {

    @Bean
    @Qualifier("transactionStatusSet")
    fun transactionStatusSet(): MutableSet<TransactionStatus> {
        return mutableSetOf()
    }
}