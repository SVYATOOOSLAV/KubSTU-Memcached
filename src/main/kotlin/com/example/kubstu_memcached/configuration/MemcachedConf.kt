package com.example.kubstu_memcached.configuration

import net.spy.memcached.MemcachedClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress

@Configuration
class MemcachedConf {
    @Bean
    fun getMemcachedClient() = MemcachedClient(InetSocketAddress("127.0.0.1", 11210))
}