package com.example.kubstu_memcached.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.cache")
data class KubstuMemcachedConf(
    var expirationTime: Int
)