package com.logistics.dataholder

enum class Priority {
    URGENT,
    STANDARD,
    LOW
}

data class Package(
    val packageId: String,
    val destinationHub: String,
    val priority: Priority,
    val weight: Double
)

data class PackageParseResult(
    val packages: List<Package>,
    val warnings: List<String>
)