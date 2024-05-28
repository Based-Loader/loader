package com.kloader.api.data


data class ModProperties(
    val id: String,
    val displayName: String = id,
    val version: String = "unknown",
    val author: String = "unknown",
    val main: Array<String> = arrayOf(),
    val contact: Contact = Contact()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModProperties

        if (id != other.id) return false
        if (displayName != other.displayName) return false
        if (version != other.version) return false
        if (author != other.author) return false
        if (!main.contentEquals(other.main)) return false
        if (contact != other.contact) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + version.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + main.contentHashCode()
        result = 31 * result + contact.hashCode()
        return result
    }
}

data class Contact(
    val website: String = "unknown",
    val sources: String = "unknown"
)