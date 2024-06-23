package com.example.kubstu_memcached.integrationTest

import com.example.kubstu_memcached.service.MemService
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
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
        val fetchedValue: String = memService.getValueByKey(key) as String

        assertSoftly {
            fetchedValue shouldBe value
        }
    }

    @Test
    fun `try to get not existing value test`() {
        assertSoftly {
            memService.getValueByKey("qwerty") shouldBe null
        }
    }

    @Test
    fun `delete value from cache test`() {
        val key = "myKey"
        val value = "myValue"

        memService.pushKeyValue(key, value)
        val fetchedValue: String = memService.getValueByKey(key) as String

        val deleteResult = memService.deleteValueFromCache(key)

        val deletedValue = memService.getValueByKey(key)

        assertSoftly {
            fetchedValue shouldBe value
            deleteResult shouldBe true
            deletedValue shouldBe null
        }
    }

    @Test
    fun `clear cache test`() {
        val key1 = "myKey1"
        val value1 = "myValue1"
        val key2 = "myKey2"
        val value2 = "myValue2"

        memService.pushKeyValue(key1, value1)
        memService.pushKeyValue(key2, value2)

        val fetchedValue1: String = memService.getValueByKey(key1) as String
        val fetchedValue2: String = memService.getValueByKey(key2) as String

        memService.clearCache()

        assertSoftly {
            fetchedValue1 shouldBe value1
            fetchedValue2 shouldBe value2
            memService.getValueByKey(key1) shouldBe null
            memService.getValueByKey(key2) shouldBe null
        }
    }
}