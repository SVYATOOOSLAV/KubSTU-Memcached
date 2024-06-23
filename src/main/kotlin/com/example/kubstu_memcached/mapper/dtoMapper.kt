package com.example.kubstu_memcached.mapper

import com.example.kubstu_memcached.controller.SpaceShipResponseDTO
import com.example.kubstu_memcached.model.SpaceShip

fun SpaceShip.toDto(): SpaceShipResponseDTO {
    return SpaceShipResponseDTO(
        id = id ?: -1,
        model = model ?: "",
        captain = captain ?: "",
        fuel = fuel ?: 0
    )
}