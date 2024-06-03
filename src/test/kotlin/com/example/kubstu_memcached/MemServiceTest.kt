package com.example.kubstu_memcached

import com.example.kubstu_memcached.services.MemService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemServiceTest {

    @Autowired
    lateinit var memService: MemService<String>

    @BeforeEach
    fun clearMemory() {
        memService.clearCache()
    }

    @Test
    fun `get value from cache test`() {
        val key = "myKey"
        val value = "myValue"

        memService.pushKeyValue(key, value)
        val fetchedValue: String = memService.getValueFromKey(key) as String

        assertEquals(value, fetchedValue)
    }

    @Test
    fun `try to get not existing value test`() {
        // return null value
        assertNull(memService.getValueFromKey("qwerty"))
    }

    @Test
    fun `delete value from cache test`() {
        val key = "myKey"
        val value = "myValue"

        memService.pushKeyValue(key, value)
        val fetchedValue: String = memService.getValueFromKey(key) as String
        assertEquals(value, fetchedValue)

        val deleteResult = memService.deleteValueFromCache(key)
        assertTrue(deleteResult)

        val deletedValue = memService.getValueFromKey(key)
        assertNull(deletedValue)
    }

    @Test
    fun `clear cache test`() {
        val key1 = "myKey1"
        val value1 = "myValue1"
        val key2 = "myKey2"
        val value2 = "myValue2"

        memService.pushKeyValue(key1, value1)
        memService.pushKeyValue(key2, value2)

        val fetchedValue1: String = memService.getValueFromKey(key1) as String
        val fetchedValue2: String = memService.getValueFromKey(key2) as String

        assertEquals(value1, fetchedValue1)
        assertEquals(value2, fetchedValue2)

        memService.clearCache()

        assertNull(memService.getValueFromKey(key1))
        assertNull(memService.getValueFromKey(key2))
    }
}