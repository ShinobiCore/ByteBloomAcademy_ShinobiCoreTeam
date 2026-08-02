package com.logistics.domain.model
import com.logistics.dataholder.Priority
data class Package(
    val id: String,
    val weight: Double,
    val priority: Priority,
    var origin: Warehouse? = null,
    var destination: Warehouse? = null
)