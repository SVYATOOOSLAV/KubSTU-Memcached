package com.example.kubstu_memcached.controller

import com.example.kubstu_memcached.mapper.toDto
import com.example.kubstu_memcached.service.SpaceShipService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SpaceShipController : SpaceShipApi {

    @Autowired
    private lateinit var spaceShipService: SpaceShipService

    override fun createSpaceShip(spaceshipRequestDTO: SpaceShipRequestDTO): ResponseEntity<SpaceShipResponseDTO> {
        return ResponseEntity.ok(
            spaceShipService.createSpaceShip(spaceshipRequestDTO).toDto()
        )
    }

    override fun getSpaceShipById(id: Long): ResponseEntity<SpaceShipResponseDTO> {
        return ResponseEntity.ok(
            spaceShipService.getSpaceShipById(id).toDto()
        )
    }

    override fun getSpaceShips(): ResponseEntity<List<SpaceShipResponseDTO>> {
        return ResponseEntity.ok(
            spaceShipService.getSpaceShips().map {
                it.toDto()
            }
        )
    }
}