package com.logistics.domain.strategy

import com.logistics.domain.model.Package
import com.logistics.domain.model.Priority
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle

class EcoStrategy : DispatchStrategy {
    private val priorityMultipliers = mapOf(
        Priority.URGENT to 1.2,
        Priority.STANDARD to 1.0,
        Priority.LOW to 0.9
    )

    override fun calculateTransitCost(packageItem: Package, route: Route, vehicle: Vehicle): Double {
        val distanceCost = route.distanceKm * vehicle.costPerKm * 0.85
        val weightCost = packageItem.weight * 1.1
        return (distanceCost + weightCost) * getPriorityMultiplier(packageItem)
    }

    override fun getPriorityMultiplier(packageItem: Package): Double {
        return priorityMultipliers.getValue(packageItem.priority)
    }
}
