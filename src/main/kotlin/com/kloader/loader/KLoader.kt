package com.kloader.loader

import com.kloader.discovery.IDiscoveryStrategy
import com.kloader.discovery.type.ModDirectoryDiscoveryStrategy
import com.kloader.loader.properties.LoaderProperties
import java.io.File

/**
 * Everything from discovery to loading is handled here.
 */
object KLoader {
    private val discoveryStrategies = mutableListOf<IDiscoveryStrategy>()

    /**
     * Initialize KLoader. This should only be called once.
     */
    internal fun initialize(properties: LoaderProperties) {
        if(!properties.disableDefaultStrategy) {
            discoveryStrategies.add(ModDirectoryDiscoveryStrategy(
                File("mods")
            ))
        }

        discoveryStrategies.addAll(properties.additionalStrategies)

        properties.plugins.forEach { it.apply(this) } // Apply plugins
    }
}