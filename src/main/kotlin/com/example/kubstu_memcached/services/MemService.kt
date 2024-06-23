package com.example.kubstu_memcached.services

import com.example.kubstu_memcached.configuration.KubstuMemcachedConf
import mu.KotlinLogging
import net.spy.memcached.MemcachedClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
@Suppress("UNCHECKED_CAST")
@EnableConfigurationProperties(KubstuMemcachedConf::class)
class MemService<T>(
    private val kubstuMemcachedConf: KubstuMemcachedConf
) {

    @Autowired
    private lateinit var mcc: MemcachedClient

    init {
        logger.debug("Connection to server successfully")
    }

    fun pushKeyValue(key: String, value: T) {
        val action = mcc.set(key, kubstuMemcachedConf.expirationTime, value).get()
        val done = if (action) "completed" else "not completed"
        logger.debug { "$key and $value have been sent to memcahed with status $done" }
    }

    fun getValueByKey(key: String): T? {
        logger.debug { "Trying to get value from memcached by key: $key" }
        val value = mcc[key] as T?
        logger.debug { "Successfully got value: $value by key: $key" }

        return value
    }

    fun deleteValueFromCache(key: String): Boolean {
        logger.debug { "Trying to remove value from memcached by key: $key" }
        val flag = mcc.delete(key).get()
        logger.debug { "Successfully removed value by key: $key" }

        return flag
    }

    fun clearCache() {
        logger.debug { "Removed all caches" }
        mcc.flush()
    }
}