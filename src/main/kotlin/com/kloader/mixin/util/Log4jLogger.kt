package com.kloader.mixin.util

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.spongepowered.asm.logging.ILogger
import org.spongepowered.asm.logging.Level

/**
 * Implementation of ILogger for Log4j.
 */
internal class Log4jLogger(private val name: String) : ILogger {
    private val backingLogger: Logger = LogManager.getLogger("KL/Mixins")

    override fun getId(): String {
        return name
    }

    override fun getType(): String {
        return "log4j"
    }

    override fun catching(level: Level, t: Throwable) {
        backingLogger.catching(l4j(level), t)
    }

    override fun catching(t: Throwable) {
        backingLogger.catching(t)
    }

    override fun debug(message: String, vararg params: Any) {
        backingLogger.debug(message, params)
    }

    override fun debug(message: String, t: Throwable) {
        backingLogger.debug(message, t)
    }

    override fun error(message: String, vararg params: Any) {
        backingLogger.error(message, params)
    }

    override fun error(message: String, t: Throwable) {
        backingLogger.error(message, t)
    }

    override fun fatal(message: String, vararg params: Any) {
        backingLogger.fatal(message, params)
    }

    override fun fatal(message: String, t: Throwable) {
        backingLogger.fatal(message, t)
    }

    override fun info(message: String, vararg params: Any) {
        backingLogger.info(message, params)
    }

    override fun info(message: String, t: Throwable) {
        backingLogger.info(message, t)
    }

    override fun log(level: Level, message: String, vararg params: Any) {
        backingLogger.log(l4j(level), message, params)
    }

    override fun log(level: Level, message: String, t: Throwable) {
        backingLogger.log(l4j(level), message, t)
    }

    override fun <T : Throwable?> throwing(t: T): T {
        return backingLogger.throwing(t)
    }

    override fun trace(message: String, vararg params: Any) {
        backingLogger.trace(message, params)
    }

    override fun trace(message: String, t: Throwable) {
        backingLogger.trace(message, t)
    }

    override fun warn(message: String, vararg params: Any) {
        // TODO Auto-generated method stub
    }

    override fun warn(message: String, t: Throwable) {
        // TODO Auto-generated method stub
    }

    companion object {
        private fun l4j(level: Level): org.apache.logging.log4j.Level {
            return when (level) {
                Level.DEBUG -> org.apache.logging.log4j.Level.DEBUG
                Level.ERROR -> org.apache.logging.log4j.Level.ERROR
                Level.FATAL -> org.apache.logging.log4j.Level.FATAL
                Level.TRACE -> org.apache.logging.log4j.Level.TRACE
                Level.WARN -> org.apache.logging.log4j.Level.WARN
                Level.INFO -> org.apache.logging.log4j.Level.INFO
                else -> org.apache.logging.log4j.Level.INFO
            }
        }
    }
}