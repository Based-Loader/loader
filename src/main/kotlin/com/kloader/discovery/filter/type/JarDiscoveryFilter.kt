package com.kloader.discovery.filter.type

import com.kloader.discovery.filter.DiscoveryFilter
import java.io.File

class JarDiscoveryFilter : DiscoveryFilter {
    override fun canDiscover(bean: Any): Boolean {
        if(bean is String) { // Assume it is a path
            if(bean.endsWith(".jar")) return true
        } else if(bean is File) {
            if(bean.extension == "jar") {
                return true
            }
        }
        return false
    }
}