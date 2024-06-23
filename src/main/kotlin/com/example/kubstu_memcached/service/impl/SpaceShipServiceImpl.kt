package com.example.kubstu_memcached.service.impl

import com.example.kubstu_memcached.controller.SpaceShipRequestDTO
import com.example.kubstu_memcached.model.SpaceShip
import com.example.kubstu_memcached.repository.SpaceshipRepository
import com.example.kubstu_memcached.service.MemService
import com.example.kubstu_memcached.service.SpaceShipService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Suppress("UNCHECKED_CAST")
@Service
class SpaceShipServiceImpl : SpaceShipService {

    @Autowired
    private lateinit var repository: SpaceshipRepository

    @Autowired
    private lateinit var memcachedService: MemService<SpaceShip>

    override fun getSpaceShips(): List<SpaceShip> {
        logger.info { "Trying to get spaceships from DB" }
        val spaceShips = repository.findAll() as List<SpaceShip>
        logger.info { "Successfully got spaceships from DB" }

        return spaceShips
    }

    override fun getSpaceShipById(id: Long): SpaceShip {
        val spaceship = memcachedService.getValueByKey(id.toString())

        if (spaceship == null) {
            logger.info { "Trying to get spaceship from DB by id: $id" }
            val spaceshipFromDB = repository.findById(id).get()
            memcachedService.pushKeyValue(spaceshipFromDB.id.toString(), spaceshipFromDB)
            logger.info { "Successfully got spaceship from PostgreSQL: $spaceshipFromDB" }
            return spaceshipFromDB
        }

        return spaceship
    }

    override fun createSpaceShip(spaceShipRequestDTO: SpaceShipRequestDTO): SpaceShip {
        logger.info { "Save to DB new spaceship" }

        val spaceship = repository.save(
            SpaceShip(
                model = spaceShipRequestDTO.model,
                captain = spaceShipRequestDTO.captain,
                fuel = spaceShipRequestDTO.fuel
            )
        )

        logger.info { "Successfully saved new spaceship" }

        return spaceship
    }
}