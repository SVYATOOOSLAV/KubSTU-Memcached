package com.example.kubstu_memcached.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "Spaceship")
data class SpaceShip(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val model: String? = null,
    val captain: String? = null,
    val fuel: Int? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
