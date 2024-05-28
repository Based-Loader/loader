package com.kloader.api.data


data class ModProperties(
    val id: String,
    val displayName: String = id,
    val version: String = "unknown",
    val author: String = "unknown",
    val contact: Contact = Contact(),
    val main: String,
    val mixins: List<String> = emptyList(),
    val dependencies: List<Dependency> = emptyList(),
)

data class Contact(
    val website: String = "unknown",
    val sources: String = "unknown"
)

data class Dependency(
    val id: String,
    val version: String
)
