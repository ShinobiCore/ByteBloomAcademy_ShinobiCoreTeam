package com.logistics.parsers

import com.logistics.dataholder.Warehouse
import com.logistics.dataholder.WarehouseParseResult
import com.logistics.utils.CsvUtils
import java.io.File

object WarehouseParser {

    fun processFile(filePath: String): WarehouseParseResult {
        val warehouses = mutableListOf<Warehouse>()
        val warnings = mutableListOf<String>()
        val file = File(filePath)

        if (!file.exists()) {
            return WarehouseParseResult(emptyList(), listOf("File not found: $filePath"))
        }

        val lines = file.readLines()
        if (lines.isEmpty()) {
            return WarehouseParseResult(emptyList(), listOf("File is empty: $filePath"))
        }

        for ((index, line) in lines.drop(1).withIndex()) {
            val lineNumber = index + 2
            if (line.isBlank()) continue

            val columns = CsvUtils.splitAndTrim(line)
            if (columns.size != 3) {
                warnings.add("Line $lineNumber: Expected 3 columns, found ${columns.size}")
                continue
            }

            val warehouseId = columns[0]
            val location = columns[1]
            val capacityStr = columns[2]

            if (!isValidWarehouseId(warehouseId)) {
                warnings.add("Line $lineNumber: Invalid Warehouse ID format '$warehouseId' (expected WH-XXX)")
                continue
            }

            if (location.isBlank()) {
                warnings.add("Line $lineNumber: Location cannot be blank")
                continue
            }

            val capacity = CsvUtils.parseSafeInt(capacityStr) ?: -1
            if (capacity <= 0) {
                warnings.add("Line $lineNumber: Invalid capacity value '$capacityStr'")
                continue
            }

            warehouses.add(Warehouse(id = warehouseId, location = location, capacity = capacity))
        }

        return WarehouseParseResult(warehouses, warnings)
    }

    private fun isValidWarehouseId(id: String): Boolean {
        return id.matches(Regex("^WH-\\d{3}$"))
    }
}