package com.kloader.discovery.filter

/**
 * Filter for discovery
 */
interface DiscoveryFilter {
    fun canDiscover(bean: Any): Boolean
}