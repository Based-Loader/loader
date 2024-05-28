package com.kloader.discovery.type

import com.kloader.discovery.IDiscoveryStrategy
import com.kloader.discovery.data.DiscoveryResult
import com.kloader.discovery.filter.DiscoveryFilter
import com.kloader.discovery.filter.type.JarDiscoveryFilter
import java.io.File
import java.util.zip.ZipFile


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
                // Make sure it has a json file
                var hasJson = false
                if(!(it.length() > 0 && it.extension == "zip")) {
                    return@forEach
                }
                val file = ZipFile(it)

                file.entries().asSequence().forEach { entry ->
                    if (!entry.isDirectory && entry.name.equals("mod.json")) {
                        hasJson = true
                    }
                }

                if(hasJson) {
                    mods.add(DiscoveryResult(it.nameWithoutExtension, it))
                }
            }
        }
        return mods
    }
}