package com.example.kubstu_memcached.repository

import com.example.kubstu_memcached.model.SpaceShip
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SpaceshipRepository : CrudRepository<SpaceShip, Long> {
}