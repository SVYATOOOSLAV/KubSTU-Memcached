package com.example.kubstu_memcached.services

import com.example.kubstu_memcached.models.SpaceShip
import net.spy.memcached.MemcachedClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

private val logger = Logger.getLogger("")

@Service
class SpaceShipMemService {

    @Autowired
    private lateinit var mcc: MemcachedClient

    fun pushKeySpaceShip(key: String, value: SpaceShip) {
        logger.info("Connection to server sucessfully")
        val done = mcc.set(key, 900, value).get()
        logger.info("Is done:$done")
        logger.info("$key and $value have been sent to memcahed")
    }

    fun getSpaceShip(key: String?): SpaceShip? {
        val spaceShip = mcc[key] as SpaceShip?
        logger.info("$spaceShip was obtained from memcahed")
        return spaceShip
    }

    fun deleteSpaceShipFromCache(key: String): Boolean {
        return mcc.delete(key).get()
    }

    fun clearCache() {
        mcc.flush()
    }
}

