package com.logistics.domain.model

data class Vehicle(
    val vehicleId: String,
    val maxCapacityKg: Double,
    val costPerKm: Double,
    var currentHub: Warehouse? = null
)