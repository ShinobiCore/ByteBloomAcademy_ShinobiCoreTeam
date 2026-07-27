package com.logistics.dataholder

data class VehicleParseResult(
    val vehicles: List<VehicleRaw>,
    val warnings: List<String>
)


data class VehicleRaw(
    val vehicleId: String,
    val currentHubId: String,
    val maxCapacityKg: Double,
    val costPerKm: Double
)