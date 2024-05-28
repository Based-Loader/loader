package com.kloader.loader.properties

import com.kloader.api.plugin.LoaderPlugin
import com.kloader.discovery.IDiscoveryStrategy

/**
 * The VM options parsed into a class.
 */
internal data class LoaderProperties(
    val plugins: Array<LoaderPlugin>,
    val additionalStrategies: Array<IDiscoveryStrategy>,
    val programArguments: Array<String>,
    val disableDefaultStrategy: Boolean,
    val mainClass: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoaderProperties

        return plugins.contentEquals(other.plugins)
    }

    override fun hashCode(): Int {
        return plugins.contentHashCode()
    }
}