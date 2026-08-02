package com.logistics.domain.model

class Package(
    val id: String,
    val weight: Double,
    val priority: Priority,
    val origin: Warehouse,
    val destination: Warehouse
)
