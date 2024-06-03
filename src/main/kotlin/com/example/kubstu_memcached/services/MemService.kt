package com.example.kubstu_memcached.services

import mu.KotlinLogging
import net.spy.memcached.MemcachedClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Suppress("UNCHECKED_CAST")
@Service
class MemService<T> {

    @Autowired
    private lateinit var mcc: MemcachedClient

    fun pushKeyValue(key: String, value: T) {
        logger.info("Connection to server sucessfully")
        val done = mcc.set(key, 900, value).get()
        logger.info("isDone: $done")
        logger.info("$key and $value have been sent to memcahed")
    }

    fun getValueFromKey(key: String): T? {
        return mcc[key] as T?
    }

    fun deleteValueFromCache(key: String): Boolean {
        return mcc.delete(key).get()
    }

    fun clearCache() {
        mcc.flush()
    }
}