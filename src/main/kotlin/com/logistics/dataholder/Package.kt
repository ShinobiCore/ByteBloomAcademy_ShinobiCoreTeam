package com.logistics.dataholder

enum class Priority {
    URGENT,
    STANDARD,
    LOW
}

data class PackageRaw(
    val packageId: String,
    val destinationHubId: String,
    val weightKg: Double,
    val priority: Priority
)

data class PackageParseResult(
    val packages: List<PackageRaw>,
    val warnings: List<String>
)