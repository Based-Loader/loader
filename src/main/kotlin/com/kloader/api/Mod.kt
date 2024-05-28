package com.kloader.api

import com.kloader.api.data.ModProperties

/**
 * The superclass to all mods. It is defined in the mod's JSON.
 */
abstract class Mod {
    // Access to the mod's properties.
    lateinit var properties: ModProperties

    open fun onEnable() {}
    open fun onDisable() {}
}