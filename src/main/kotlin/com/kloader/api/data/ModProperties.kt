package com.kloader.api.data


data class ModProperties(
    val id: String,
    val displayName: String = id,
    val version: String = "unknown",
    val author: String = "unknown",
    val contact: Contact = Contact()
)

data class Contact(
    val website: String = "unknown",
    val sources: String = "unknown"
)