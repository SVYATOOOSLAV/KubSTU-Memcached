package com.example.kubstu_memcached.services

import net.spy.memcached.MemcachedClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

private val logger = Logger.getLogger("")

@Service
class MemService {

    @Autowired
    private lateinit var mcc: MemcachedClient

    fun pushKeyValue(key: String, value: Any) {
        logger.info("Connection to server sucessfully")
        val done = mcc.set(key, 900, value).get()
        logger.info("isDone: $done")
        logger.info("$key and $value have been sent to memcahed")
    }

    fun getValueFromKey(key: String): Any? {
        return mcc[key]
    }

    fun deleteValueFromCache(key: String): Boolean {
        return mcc.delete(key).get()
    }

    fun clearCache() {
        mcc.flush()
    }
}