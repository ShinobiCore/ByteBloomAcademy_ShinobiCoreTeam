package com.logistics.domain.strategy

import com.logistics.domain.model.Package
import com.logistics.domain.model.Priority
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle

class FragileStrategy : DispatchStrategy {
    private val safetyFee = 10.0
    private val distanceMultiplier = 1.1

    override fun calculateTransitCost(
        packageItem: Package,
        route: Route,
        vehicle: Vehicle
    ): Double {
        val baseCost = route.distanceKm * vehicle.costPerKm
        val distanceAdjustedCost =
            baseCost * distanceMultiplier

        return distanceAdjustedCost + safetyFee
    }


    override fun getPriorityMultiplier(
        packageItem: Package
    ): Double {
        return when (packageItem.priority) {
            Priority.STANDARD -> 1.0
            Priority.URGENT -> 1.1
            Priority.LOW -> 1.2
        }
    }
}