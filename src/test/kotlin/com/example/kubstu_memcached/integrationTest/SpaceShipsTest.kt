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
class SpaceShipsTest {

    @Autowired
    lateinit var spaceshipMemService: MemService<List<SpaceShip>>

    @BeforeEach
    fun clearMemory() {
        spaceshipMemService.clearCache()
    }

    @Test
    fun `memcached should remember list of objects`() {
        val key = "ships"
        val listOfShips = listOf(
            SpaceShip("Pyramid", "Mike", 55),
            SpaceShip("Falcon", "Sarah", 40),
            SpaceShip("Barron", "Lorak", 38)
        )

        spaceshipMemService.pushKeyValue(key, listOfShips)

        val fetchedValue = spaceshipMemService.getValueByKey(key)

        assertSoftly {
            fetchedValue shouldBe listOfShips
        }
    }
}