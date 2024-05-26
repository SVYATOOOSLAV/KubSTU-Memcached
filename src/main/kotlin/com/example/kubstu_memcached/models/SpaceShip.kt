package com.example.kubstu_memcached.models

import java.io.Serializable

data class SpaceShip(
    val model: String? = null,
    val captain: String? = null,
    val fuel: Int? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
