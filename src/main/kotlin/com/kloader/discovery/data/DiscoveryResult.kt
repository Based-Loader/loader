package com.kloader.discovery.data

import com.google.gson.Gson
import com.kloader.api.data.ModProperties
import java.io.File
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

data class DiscoveryResult(
    val name: String,
    val file: File
) {
    fun getModProperties(): ModProperties {
        val gson = Gson()
        val zipFile = ZipFile(file)
        var modEntry: ZipEntry? = null

        zipFile.entries().asSequence().forEach { entry ->
            if(!entry.isDirectory && entry.name.equals("mod.json")) {
                modEntry = entry
            }
        }

        return gson.fromJson(InputStreamReader(zipFile.getInputStream(modEntry)), ModProperties::class.java)
    }
}