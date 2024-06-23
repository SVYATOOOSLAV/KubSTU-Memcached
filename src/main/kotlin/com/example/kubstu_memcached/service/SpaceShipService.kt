package com.example.kubstu_memcached.service

import com.example.kubstu_memcached.controller.SpaceShipRequestDTO
import com.example.kubstu_memcached.model.SpaceShip

interface SpaceShipService {
    fun getSpaceShips(): List<SpaceShip>
    fun getSpaceShipById(id: Long): SpaceShip
    fun createSpaceShip(spaceShipRequestDTO: SpaceShipRequestDTO): SpaceShip
}