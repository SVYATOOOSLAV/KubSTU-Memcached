package com.example.kubstu_memcached.controller

class SpaceShipRequestDTO(
    val model: String,
    val captain: String,
    val fuel: Int
)

class SpaceShipResponseDTO(
    val id: Long,
    val model: String,
    val captain: String,
    val fuel: Int
)
