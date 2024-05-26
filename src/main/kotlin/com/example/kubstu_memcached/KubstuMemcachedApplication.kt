package com.example.kubstu_memcached

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KubstuMemcachedApplication

fun main(args: Array<String>) {
    runApplication<KubstuMemcachedApplication>(*args)
}
