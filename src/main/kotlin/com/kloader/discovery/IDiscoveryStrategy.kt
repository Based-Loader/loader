package com.kloader.discovery

import com.kloader.discovery.data.DiscoveryResult

/**
 * Base class for a mod discovery strategy.
 */
interface IDiscoveryStrategy {
    /**
     * Returns a list of the discovered mods.
     */
    fun discover(): List<DiscoveryResult>
}