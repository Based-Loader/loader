package com.kloader.main

import com.kloader.api.plugin.LoaderPlugin
import com.kloader.discovery.IDiscoveryStrategy
import com.kloader.loader.KLoader
import com.kloader.loader.properties.LoaderProperties
import com.kloader.loader.properties.VMOptions

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Parse properties
        val plugins = mutableListOf<LoaderPlugin>()
        val strategies = mutableListOf<IDiscoveryStrategy>()

        VMOptions.plugins.split(",").forEach {
            try {
                val clazz = Class.forName(it)
                val instance = clazz.getDeclaredConstructor().newInstance()

                if(instance is LoaderPlugin) {
                    plugins.add(instance)
                }
            } catch (_: Exception) {}
        }

        VMOptions.additionalStrategies.split(",").forEach {
            try {
                val clazz = Class.forName(it)
                val instance = clazz.getDeclaredConstructor().newInstance()

                if(instance is IDiscoveryStrategy) {
                    strategies.add(instance)
                }
            } catch (_: Exception) {}
        }

        val properties = LoaderProperties(
            plugins.toTypedArray(),
            strategies.toTypedArray(),
            VMOptions.disableDefaultStrategies
        )

        // Launch!
        KLoader.initialize(properties)
    }
}