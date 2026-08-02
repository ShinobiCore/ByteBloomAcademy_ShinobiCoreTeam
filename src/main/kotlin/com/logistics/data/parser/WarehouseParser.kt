package com.logistics.data.parser

import com.logistics.data.raw.WarehouseRaw
import com.logistics.utils.CsvUtils
import java.io.File

object WarehouseParser {

    fun processFile(filePath: String): ParseResult<WarehouseRaw> {
        val warehouses = mutableListOf<WarehouseRaw>()
        val warnings = mutableListOf<String>()
        val file = File(filePath)

        if (!file.exists()) {
            return ParseResult(emptyList(), listOf("File not found: $filePath"))
        }

        val lines = file.readLines()
        if (lines.isEmpty()) {
            return ParseResult(emptyList(), listOf("File is empty: $filePath"))
        }

        for ((index, line) in lines.drop(1).withIndex()) {
            val lineNumber = index + 2
            if (line.isBlank()) continue

            val columns = CsvUtils.splitAndTrim(line)
            if (columns.size != 5) {
                warnings.add("Line $lineNumber: Expected 5 columns, found ${columns.size}")
                continue
            }

            val warehouseId = columns[0]
            val name = columns[1]
            val regionalZone = columns[2]
            val latitude = columns[3]
            val longitude = columns[4]



            if (!isValidWarehouseId(warehouseId)) {
                warnings.add("Line $lineNumber: Invalid Warehouse ID format '$warehouseId' (expected WH-XXX)")
                continue
            }

            if (name.isBlank()) {
                warnings.add("Line $lineNumber: name cannot be blank")
                continue
            }

            if (regionalZone.isBlank()) {
                warnings.add("Line $lineNumber: regionalZone cannot be blank")
                continue
            }

            if (latitude.isBlank()) {
                warnings.add("Line $lineNumber: latitude cannot be blank")
                continue
            }

            if (longitude.isBlank()) {
                warnings.add("Line $lineNumber: longitude cannot be blank")
                continue
            }

            warehouses.add(
                WarehouseRaw(
                    id = warehouseId,
                    name = name,
                    regionalZone = regionalZone,
                    latitude = CsvUtils.parseSafeDouble(latitude),
                    longitude = CsvUtils.parseSafeDouble(longitude)
                )
            )
        }

        return ParseResult(warehouses, warnings)
    }

    private fun isValidWarehouseId(id: String): Boolean {
        return id.matches(Regex("^WH-\\d{3}$"))
    }
}