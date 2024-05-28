package com.kloader.mixin

import com.kloader.mixin.util.ClassWrapper
import com.kloader.mixin.util.Log4jLogger
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual
import org.spongepowered.asm.launch.platform.container.IContainerHandle
import org.spongepowered.asm.logging.ILogger
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.transformer.IMixinTransformer
import org.spongepowered.asm.mixin.transformer.IMixinTransformerFactory
import org.spongepowered.asm.service.*
import org.spongepowered.asm.util.Constants
import org.spongepowered.asm.util.ReEntranceLock
import java.io.IOException
import java.io.InputStream
import java.net.URL


/**
 * Implementation of IMixinService for ClassWrapper.
 */
class KLMixinService : IMixinService, IClassProvider, IClassBytecodeProvider {
    private val lock = ReEntranceLock(1)
    private val container: IContainerHandle = ContainerHandleVirtual(name)

    override fun getName(): String {
        return "wrapper"
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun prepare() {
    }

    override fun getInitialPhase(): MixinEnvironment.Phase? {
        return null
    }

    override fun offer(internal: IMixinInternal) {
        if (internal is IMixinTransformerFactory) transformer = internal.createTransformer()
    }

    override fun init() {
    }

    override fun beginPhase() {
    }

    override fun checkEnv(bootSource: Any) {
    }

    override fun getReEntranceLock(): ReEntranceLock {
        return lock
    }

    override fun getClassProvider(): IClassProvider {
        return this
    }

    override fun getBytecodeProvider(): IClassBytecodeProvider {
        return this
    }

    override fun getTransformerProvider(): ITransformerProvider? {
        return null
    }

    override fun getClassTracker(): IClassTracker? {
        return null
    }

    override fun getAuditTrail(): IMixinAuditTrail? {
        return null
    }

    override fun getPlatformAgents(): Collection<String> {
        return emptyList()
    }

    override fun getPrimaryContainer(): IContainerHandle {
        return container
    }

    override fun getMixinContainers(): Collection<IContainerHandle> {
        return emptyList()
    }

    override fun getResourceAsStream(name: String?): InputStream {
        return ClassLoader.getSystemResourceAsStream(name)
    }

    override fun getSideName(): String {
        return Constants.SIDE_CLIENT
    }

    override fun getMinCompatibilityLevel(): MixinEnvironment.CompatibilityLevel? {
        return null
    }

    override fun getMaxCompatibilityLevel(): MixinEnvironment.CompatibilityLevel? {
        return null
    }

    override fun getLogger(name: String): ILogger {
        return Log4jLogger(name)
    }

    // class provider
    override fun getClassPath(): Array<URL> {
        throw UnsupportedOperationException()
    }

    @Throws(ClassNotFoundException::class)
    override fun findClass(name: String): Class<*> {
        return ClassWrapper.instance.loadClass(name)
    }

    @Throws(ClassNotFoundException::class)
    override fun findClass(name: String, initialize: Boolean): Class<*> {
        return Class.forName(name, initialize, ClassWrapper.instance)
    }

    @Throws(ClassNotFoundException::class)
    override fun findAgentClass(name: String, initialize: Boolean): Class<*> {
        return Class.forName(name, initialize, ClassWrapper::class.java.classLoader)
    }

    // bytecode provider
    @Throws(ClassNotFoundException::class, IOException::class)
    override fun getClassNode(name: String): ClassNode {
        return getClassNode(name, true)
    }

    @Throws(ClassNotFoundException::class, IOException::class)
    override fun getClassNode(name: String, runTransformers: Boolean): ClassNode {
        val bytes =
            ClassWrapper.instance.getTransformedBytes(name.replace('/', '.'), false)
                ?: throw ClassNotFoundException(name)

        val reader = ClassReader(bytes)
        val node = ClassNode()
        reader.accept(node, 0)
        return node
    }

    companion object {
        var transformer: IMixinTransformer? = null
    }
}