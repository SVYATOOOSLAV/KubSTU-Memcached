package com.example.kubstu_memcached

import com.example.kubstu_memcached.models.SpaceShip
import com.example.kubstu_memcached.services.MemService
import org.junit.jupiter.api.Assertions.*
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
        val fetchedSpaceShip: SpaceShip? = spaceshipMemService.getValueFromKey(key)

        assertEquals(spaceShip, fetchedSpaceShip)
    }

    @Test
    fun `try to get non-existing space ship test`() {
        val key = "nonExistentKey"
        val fetchedSpaceShip: SpaceShip? = spaceshipMemService.getValueFromKey(key)

        assertNull(fetchedSpaceShip)
    }

    @Test
    fun `delete space ship from cache test`() {
        val key = "myship2"
        val spaceShip = SpaceShip("Pyramid", "Mike", 55)

        spaceshipMemService.pushKeyValue(key, spaceShip)
        val fetchedSpaceShip: SpaceShip? = spaceshipMemService.getValueFromKey(key)
        assertEquals(spaceShip, fetchedSpaceShip)

        val deleteResult = spaceshipMemService.deleteValueFromCache(key)
        assertTrue(deleteResult)

        val deletedSpaceShip: SpaceShip? = spaceshipMemService.getValueFromKey(key)
        assertNull(deletedSpaceShip)
    }

    @Test
    fun `clear cache test`() {
        val key1 = "myship1"
        val spaceShip1 = SpaceShip("Pyramid", "Mike", 55)
        val key2 = "myship2"
        val spaceShip2 = SpaceShip("Falcon", "Sarah", 40)

        spaceshipMemService.pushKeyValue(key1, spaceShip1)
        spaceshipMemService.pushKeyValue(key2, spaceShip2)

        val fetchedSpaceShip1: SpaceShip? = spaceshipMemService.getValueFromKey(key1)
        val fetchedSpaceShip2: SpaceShip? = spaceshipMemService.getValueFromKey(key2)

        assertEquals(spaceShip1, fetchedSpaceShip1)
        assertEquals(spaceShip2, fetchedSpaceShip2)

        spaceshipMemService.clearCache()

        assertNull(spaceshipMemService.getValueFromKey(key1))
        assertNull(spaceshipMemService.getValueFromKey(key2))
    }
}