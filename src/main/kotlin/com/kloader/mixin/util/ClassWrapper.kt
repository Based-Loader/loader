package com.kloader.mixin.util

import com.kloader.mixin.KLMixinService
import org.spongepowered.asm.mixin.MixinEnvironment
import java.net.URL
import java.net.URLClassLoader

class ClassWrapper internal constructor(urls: Array<URL?>?) : URLClassLoader(urls) {
    private val upstream: ClassLoader = getSystemClassLoader()

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String, resolve: Boolean): Class<*> {
        synchronized(getClassLoadingLock(name)) {
            val preexisting = findLoadedClass(name)
            if (preexisting != null) return preexisting

            if (!name.startsWith("org.spongepowered.asm.synthetic.") && !name.startsWith("javax.vecmath.")) {
                for (exclude in EXCLUDED_CLASSES) if (name == exclude) return upstream.loadClass(
                    name
                )

                for (exclude in EXCLUDED_PACKAGES) if (name.startsWith(exclude) && name[exclude.length] == '.'
                ) return upstream.loadClass(name)
            }

            val found = findClass(name) ?: return upstream.loadClass(name)
            return found
        }
    }

    @Throws(ClassNotFoundException::class)
    override fun findClass(name: String): Class<*>? {
        val data = getTransformedBytes(name) ?: return null

        return defineClass(name, data, 0, data.size)
    }

    @Throws(ClassNotFoundException::class)
    fun getTransformedBytes(name: String): ByteArray {
        return getTransformedBytes(name, true)
    }

    @Throws(ClassNotFoundException::class)
    fun getTransformedBytes(name: String, mixin: Boolean): ByteArray {
        try {
            val resource = getResource(getClassFileName(name))
                ?: return KLMixinService.transformer!!
                    .generateClass(MixinEnvironment.getDefaultEnvironment(), name)

            var data: ByteArray
            resource.openStream().use { `in` ->
                data = `in`.readAllBytes()
            }
            if (mixin) data = KLMixinService.transformer!!.transformClass(
                MixinEnvironment.getDefaultEnvironment(), name,
                data
            )

            return data
        } catch (error: Throwable) {
            throw ClassNotFoundException(name, error)
        }
    }

    init {
        instance = this
        Thread.currentThread().contextClassLoader = this
    }

    companion object {
        lateinit var instance: ClassWrapper

        private val EXCLUDED_PACKAGES = arrayOf(
            "java",
            "javax",
            "sun",
            "com.sun",
            "jdk",
            "org.apache",
            "io.netty",
            "com.mojang.authlib",
            "com.mojang.util",
            "com.google",
            "net.hypixel",
            "org.spongepowered",
        )
        private val EXCLUDED_CLASSES = arrayOf(
            "org.lwjgl.Version"
        )

        private fun getClassFileName(name: String): String {
            return name.replace('.', '/') + ".class"
        }

        init {
            registerAsParallelCapable()
        }
    }
}