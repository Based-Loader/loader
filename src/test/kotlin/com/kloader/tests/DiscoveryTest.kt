package com.kloader.tests

import com.kloader.discovery.type.ModDirectoryDiscoveryStrategy
import java.io.File

/**
 * Tests the discovery.
 */
object DiscoveryTest {
    private val ModsFolder = File("run/mods")

    init {
        if(!ModsFolder.exists()){
            ModsFolder.mkdirs()
        }
    }

    private val discoveryStrategies = arrayOf(
        ModDirectoryDiscoveryStrategy(ModsFolder)
    )

    fun test() {
        discoveryStrategies.forEach { strategy ->
            println("Testing discovery: ${strategy.javaClass.simpleName}")

            strategy.discover().forEach {
                println(
                    "[${strategy.javaClass.simpleName}] Discovered ${it.name}"
                )
            }
        }
    }
}

fun main() {
    DiscoveryTest.test()
}