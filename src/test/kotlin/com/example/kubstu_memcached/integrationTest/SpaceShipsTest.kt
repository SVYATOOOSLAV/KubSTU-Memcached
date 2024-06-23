package com.example.kubstu_memcached.integrationTest

import com.example.kubstu_memcached.model.SpaceShip
import com.example.kubstu_memcached.service.MemService
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
            SpaceShip(model = "Pyramid", captain = "Mike", fuel = 55),
            SpaceShip(model = "Falcon", captain = "Sarah", fuel = 40),
            SpaceShip(model = "Barron", captain = "Lorak", fuel = 38)
        )

        spaceshipMemService.pushKeyValue(key, listOfShips)

        val fetchedValue = spaceshipMemService.getValueByKey(key)

        assertSoftly {
            fetchedValue shouldBe listOfShips
        }
    }
}