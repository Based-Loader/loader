package com.kloader.loader

import com.kloader.api.Mod
import com.kloader.discovery.IDiscoveryStrategy
import com.kloader.discovery.data.DiscoveryResult
import com.kloader.discovery.type.ModDirectoryDiscoveryStrategy
import com.kloader.loader.properties.LoaderProperties
import org.apache.logging.log4j.LogManager
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.Mixins
import java.io.File
import java.net.URL
import java.net.URLClassLoader


/**
 * Everything from discovery to loading is handled here.
 */
object KLoader {
    private val logger = LogManager.getLogger("KLoader/Core")

    private val discoveryStrategies = mutableListOf<IDiscoveryStrategy>()

    private val loadedMods = mutableListOf<Mod>()
    private val idToInstance = mutableMapOf<String, Mod>()
    private val discoveredMods = mutableListOf<DiscoveryResult>()

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

        // Discover mods
        discoveryStrategies.forEach { it.discover().forEach(discoveredMods::add) }

        logger.info("Attempting to load ${discoveredMods.size} mods")

        discoveredMods.forEach {
            val modProperties = it.getModProperties()

            val classLoader = URLClassLoader.newInstance(arrayOf<URL>(it.file.toURI().toURL()), javaClass.classLoader)
            val clazz = Class.forName(modProperties.main, true, classLoader)

            val instance = clazz.asSubclass(Mod::class.java).getDeclaredConstructor().newInstance()

            instance.properties = modProperties

            idToInstance.putIfAbsent(modProperties.id, instance)

            loadedMods.add(instance)
        }

        MixinBootstrap.init()

        val env = MixinEnvironment.getDefaultEnvironment()

        loadedMods.forEach { mod ->
            mod.properties.mixins.forEach {
                Mixins.addConfiguration(it)
            }
        }

        if (env.obfuscationContext == null) {
            env.obfuscationContext = "notch"
        }

        env.setSide(MixinEnvironment.Side.CLIENT)

        // Enable all mods
        loadedMods.forEach { it.onEnable() }
    }
}