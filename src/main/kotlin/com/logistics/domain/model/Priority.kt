package com.logistics.domain.model

enum class Priority(val rank: Int) {
    URGENT(3),
    STANDARD(2),
    LOW(1);

    companion object {
        fun fromText(value: String): Priority {
            return entries.firstOrNull { it.name == value.trim().uppercase() } ?: LOW
        }
    }
}
