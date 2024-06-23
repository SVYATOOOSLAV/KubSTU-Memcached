package com.example.kubstu_memcached.integrationTest

import com.example.kubstu_memcached.model.SpaceShip
import com.example.kubstu_memcached.repository.SpaceshipRepository
import com.example.kubstu_memcached.service.MemService
import com.example.kubstu_memcached.service.SpaceShipService
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class CacheTest {

    @Autowired
    private lateinit var spaceShipService: SpaceShipService

    @MockBean
    lateinit var spaceshipMemService: MemService<SpaceShip>

    @MockBean
    private lateinit var repository: SpaceshipRepository

    @Test
    fun `find spaceship by id should be called once`() {
        val mockedResponse = SpaceShip(
            id = 1,
            model = "SomeModel",
            captain = "RandomName",
            fuel = 55
        )

        `when`(spaceshipMemService.getValueByKey("1")).thenReturn(null).thenReturn(mockedResponse)
        `when`(repository.findById(1)).thenReturn(Optional.of(mockedResponse))

        val firstCall = spaceShipService.getSpaceShipById(1)
        val secondCall = spaceShipService.getSpaceShipById(1)

        verify(repository, times(1)).findById(1)

        assertSoftly {
            firstCall shouldBe mockedResponse
            secondCall shouldBe mockedResponse
        }
    }
}