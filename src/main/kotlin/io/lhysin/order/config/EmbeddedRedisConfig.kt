package io.lhysin.order.config

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer

@Configuration
class EmbeddedRedisConfig : InitializingBean, DisposableBean {

    private val redisPort = 6379
    private var redisServer: RedisServer? = null

    override fun destroy() {
        redisServer?.stop()
    }

    override fun afterPropertiesSet() {
        redisServer = RedisServer(redisPort)
        redisServer?.start()
    }
}