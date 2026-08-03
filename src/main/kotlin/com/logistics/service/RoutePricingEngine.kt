package com.logistics.service

import com.logistics.domain.model.Package
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle
import com.logistics.domain.strategy.DispatchStrategy

class RoutePricingEngine(private var dispatchStrategy: DispatchStrategy) {
    fun updateStrategy(dispatchStrategy: DispatchStrategy) {
        this.dispatchStrategy = dispatchStrategy
    }

    fun calculateTransitCost(packageItem: Package, route: Route, vehicle: Vehicle): Double {
        return dispatchStrategy.calculateTransitCost(packageItem, route, vehicle)
    }
}
