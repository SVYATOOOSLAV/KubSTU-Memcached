package com.example.kubstu_memcached.integrationTest

import com.example.kubstu_memcached.models.SpaceShip
import com.example.kubstu_memcached.services.MemService
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
internal class SpaceShipTest {

    @Autowired
    lateinit var spaceshipMemService: MemService<SpaceShip>

    @BeforeEach
    fun clearMemory() {
        spaceshipMemService.clearCache()
    }

    @Test
    fun `push and get space ship test`() {
        val key = "myship2"
        val spaceShip = SpaceShip("Pyramid", "Mike", 55)

        spaceshipMemService.pushKeyValue(key, spaceShip)
        val fetchedSpaceShip: SpaceShip? = spaceshipMemService.getValueByKey(key)

        assertSoftly {
            fetchedSpaceShip shouldBe spaceShip
        }
    }

    @Test
    fun `try to get non-existing space ship test`() {
        val key = "nonExistentKey"
        val fetchedSpaceShip: SpaceShip? = spaceshipMemService.getValueByKey(key)

        assertSoftly {
            fetchedSpaceShip shouldBe null
        }
    }

    @Test
    fun `delete space ship from cache test`() {
        val key = "myship2"
        val spaceShip = SpaceShip("Pyramid", "Mike", 55)

        spaceshipMemService.pushKeyValue(key, spaceShip)
        val fetchedSpaceShip: SpaceShip? = spaceshipMemService.getValueByKey(key)

        val deleteResult = spaceshipMemService.deleteValueFromCache(key)

        val deletedSpaceShip: SpaceShip? = spaceshipMemService.getValueByKey(key)

        assertSoftly {
            fetchedSpaceShip shouldBe spaceShip
            deleteResult shouldBe true
            deletedSpaceShip shouldBe null
        }
    }

    @Test
    fun `clear cache test`() {
        val key1 = "myship1"
        val spaceShip1 = SpaceShip("Pyramid", "Mike", 55)
        val key2 = "myship2"
        val spaceShip2 = SpaceShip("Falcon", "Sarah", 40)

        spaceshipMemService.pushKeyValue(key1, spaceShip1)
        spaceshipMemService.pushKeyValue(key2, spaceShip2)

        val fetchedSpaceShip1: SpaceShip? = spaceshipMemService.getValueByKey(key1)
        val fetchedSpaceShip2: SpaceShip? = spaceshipMemService.getValueByKey(key2)

        spaceshipMemService.clearCache()

        assertSoftly {
            fetchedSpaceShip1 shouldBe spaceShip1
            fetchedSpaceShip2 shouldBe spaceShip2
            spaceshipMemService.getValueByKey(key1) shouldBe null
            spaceshipMemService.getValueByKey(key2) shouldBe null
        }
    }
}