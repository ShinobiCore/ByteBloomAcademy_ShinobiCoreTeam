package com.logistics.dataholder

enum class Priority {
    LOW,
    STANDARD,
    URGENT
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