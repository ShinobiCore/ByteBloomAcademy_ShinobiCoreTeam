package com.logistics.data.builder

import com.logistics.domain.model.Package
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle
import com.logistics.domain.model.Warehouse

data class DomainGraph(
    val warehouses: List<Warehouse>,
    val packages: List<Package>,
    val routes: List<Route>,
    val vehicles: List<Vehicle>
)
