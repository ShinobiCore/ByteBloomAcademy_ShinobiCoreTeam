package com.logistics.dataholder

data class Warehouse(
    val id: String,
    val location: String,
    val capacity: Int
)
data class WarehouseParseResult(
    val warehouses: List<Warehouse>,
    val warnings: List<String>
)