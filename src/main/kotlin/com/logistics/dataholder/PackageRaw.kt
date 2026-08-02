package com.logistics.dataholder

enum class Priority {
    LOW,
    STANDARD,
    URGENT
}

data class Package(
    val packageId: String,
    val destinationHub: String,
    val priority: Priority,
    val weight: Double
)

//  تأكد من تسمية القائمة بـ packages هنا بالضبط:
data class PackageParseResult(
    val packages: List<Package>,
    val warnings: List<String>
)