package com.example.kubstu_memcached.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/spaceships")
interface SpaceShipApi {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createSpaceShip(@RequestBody spaceshipRequestDTO: SpaceShipRequestDTO): ResponseEntity<SpaceShipResponseDTO>

    @GetMapping(path = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getSpaceShipById(@PathVariable id: Long): ResponseEntity<SpaceShipResponseDTO>

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getSpaceShips(): ResponseEntity<List<SpaceShipResponseDTO>>
}