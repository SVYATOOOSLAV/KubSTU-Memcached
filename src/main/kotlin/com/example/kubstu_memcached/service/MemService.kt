package com.example.kubstu_memcached.service

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
        logger.info("Connection to Memcached successfully")
    }

    fun pushKeyValue(key: String, value: T) {
        val action = mcc.set(key, kubstuMemcachedConf.expirationTime, value).get()
        val done = if (action) "completed" else "not completed"
        logger.info { "Key:$key and Value:$value have been sent to memcahed with status $done" }
    }

    fun getValueByKey(key: String): T? {
        logger.info { "Trying to get value from memcached by key: $key" }

        val value = mcc[key] as T?
        if(value == null){
            logger.info { "Value not found by key: $key" }
        }
        else{
            logger.info { "Successfully got value from Memcached: $value by key: $key" }
        }

        return value
    }

    fun deleteValueFromCache(key: String): Boolean {
        logger.info { "Trying to remove value from memcached by key: $key" }
        val flag = mcc.delete(key).get()
        logger.debug { "Successfully removed value by key: $key" }

        return flag
    }

    fun clearCache() {
        logger.info { "Removed all caches" }
        mcc.flush()
    }
}