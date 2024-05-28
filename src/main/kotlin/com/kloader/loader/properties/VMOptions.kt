package com.kloader.loader.properties

internal object VMOptions {
    internal val plugins get() = System.getProperty("kl.plugins", "")
    internal val additionalStrategies get() = System.getProperty("kl.strategies", "")
    internal val disableDefaultStrategies get() = System.getProperty("kl.disableDefaultStrategies", "false").toBoolean()
    internal val mainClass get() = System.getProperty("kl.gameMain", "net.minecraft.client.main.Main")
}