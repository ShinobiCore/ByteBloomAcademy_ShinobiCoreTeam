package com.logistics.domain.strategy

import com.logistics.domain.model.Package
import com.logistics.domain.model.Priority
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle

class ExpressStrategy : DispatchStrategy {

    private val priorityMultipliers = mapOf(
        Priority.URGENT to 3.0,
        Priority.STANDARD to 1.8,
        Priority.LOW to 1.2
    )

    override fun calculateTransitCost(packageItem: Package, route: Route, vehicle: Vehicle): Double {
        val distanceCost = route.distanceKm * vehicle.costPerKm * 1.5
        val weightCost = packageItem.weight * 2.0
        return (distanceCost + weightCost) * getPriorityMultiplier(packageItem)
    }

    override fun getPriorityMultiplier(packageItem: Package): Double {
        return priorityMultipliers.getValue(packageItem.priority)
    }
} ;