package com.logistics.dataholder

data class Vehicle(
    val vehicleId: String,
    val currentHubId: String,
    val maxCapacityKg: Double,
    val costPerKm: Double
)
data class FleetParseResult(
    val vehicles: List<Vehicle>,
    val warnings: List<String>
)