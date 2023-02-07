package io.lhysin.order.config

import com.github.sonus21.rqueue.config.SimpleRqueueListenerContainerFactory
import com.github.sonus21.rqueue.utils.Constants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RedisConfig {

//    @Bean
//    fun redisConnectionFactory(): RedisConnectionFactory {
//        return LettuceConnectionFactory()
//    }

    @Bean
    fun simpleRqueueListenerContainerFactory(): SimpleRqueueListenerContainerFactory? {
        val simpleRqueueListenerContainerFactory = SimpleRqueueListenerContainerFactory()
        simpleRqueueListenerContainerFactory.maxNumWorkers = 1
        simpleRqueueListenerContainerFactory.pollingInterval = Constants.ONE_MILLI
        return simpleRqueueListenerContainerFactory
    }
}