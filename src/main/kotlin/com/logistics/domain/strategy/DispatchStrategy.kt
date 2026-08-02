package com.logistics.domain.strategy

import com.logistics.domain.model.Package
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle

interface DispatchStrategy {
    fun calculateTransitCost(packageItem: Package, route: Route, vehicle: Vehicle): Double

    fun getPriorityMultiplier(packageItem: Package): Double
}
