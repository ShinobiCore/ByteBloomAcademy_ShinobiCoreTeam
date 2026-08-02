package com.logistics.data.raw

import com.logistics.domain.model.Priority

data class PackageRaw(
    val id: String,
    val weight: Double,
    val priority: Priority,
    val originWarehouseId: String,
    val destinationWarehouseId: String
)

data class WarehouseRaw(
    val id: String,
    val name: String,
    val regionalZone: String,
    val latitude: Double,
    val longitude: Double
)

data class RouteRaw(
    val routeId: String,
    val distanceKm: Double,
    val typicalDelayMin: Int,
    val originWarehouseId: String,
    val destinationWarehouseId: String
)

data class VehicleRaw(
    val vehicleId: String,
    val maxCapacityKg: Double,
    val costPerKm: Double,
    val currentHubId: String
)

