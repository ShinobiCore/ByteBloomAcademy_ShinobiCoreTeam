package com.logistics.dataholder

data class Package(
    val packageId: String,
    val weight: Double,
    val priority: String,
    val destinationHub: String
)