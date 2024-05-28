package com.kloader.discovery.data

import java.io.InputStream

data class DiscoveryResult(
    val name: String,
    val inputStream: InputStream
)