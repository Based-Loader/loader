package com.kloader.discovery.type

import com.kloader.discovery.IDiscoveryStrategy
import com.kloader.discovery.data.DiscoveryResult
import com.kloader.discovery.filter.DiscoveryFilter
import com.kloader.discovery.filter.type.JarDiscoveryFilter
import java.io.File
import java.io.FileInputStream

/**
 * Discovers mods from the `mods` folder.
 */
class ModDirectoryDiscoveryStrategy(
    private val modsDirectory: File,
    private val filter: DiscoveryFilter = JarDiscoveryFilter()
) : IDiscoveryStrategy {
    override fun discover(): List<DiscoveryResult> {
        val mods = mutableListOf<DiscoveryResult>()
        modsDirectory.listFiles().forEach {
            if(filter.canDiscover(it)) {
                mods.add(DiscoveryResult(it.nameWithoutExtension, FileInputStream(it)))
            }
        }
        return mods
    }
}