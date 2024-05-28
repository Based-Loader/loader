package com.kloader.mixin

import org.spongepowered.asm.service.IGlobalPropertyService
import org.spongepowered.asm.service.IPropertyKey

class KLGlobalPropertyService : IGlobalPropertyService {
    private val props: MutableMap<IPropertyKey, Any> = HashMap()

    override fun resolveKey(name: String): IPropertyKey {
        return Key(name)
    }

    override fun <T> getProperty(key: IPropertyKey): T {
        return props[key] as T
    }

    override fun setProperty(key: IPropertyKey, value: Any) {
        props[key] = value
    }

    override fun <T> getProperty(key: IPropertyKey, defaultValue: T): T {
        return props.getOrDefault(key, defaultValue) as T
    }

    override fun getPropertyString(key: IPropertyKey, defaultValue: String): String {
        return getProperty(key, defaultValue)
    }

    internal inner class Key(
        val name: String
    ) : IPropertyKey
}