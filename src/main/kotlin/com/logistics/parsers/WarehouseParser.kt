package com.logistics.processors

import com.logistics.dataholder.Warehouse
import java.io.File

class WarehouseParser {
    private val warnings = mutableListOf<String>()

    fun processFile(filePath: String): List<Warehouse> {
        val warehouses = mutableListOf<Warehouse>()
        val file = File(filePath)

        if (!file.exists()) {
            warnings.add("File not found: $filePath")
            return emptyList()
        }

        try {
            val lines = file.readLines()

            if (lines.isEmpty()) {
                warnings.add("File is empty: $filePath")
                return emptyList()
            }

            for ((index, line) in lines.drop(1).withIndex()) {
                val lineNumber = index + 2

                if (line.isBlank()) {
                    warnings.add("Line $lineNumber: Empty line skipped")
                    continue
                }

                val cleanedLine = line.trim()
                val processedLine = handleDelimiters(cleanedLine)
                val columns = processedLine.split(",").map { it.trim() }

                when {
                    columns.isEmpty() -> {
                        warnings.add("Line $lineNumber: Empty line after processing, skipped")
                        continue
                    }
                    columns.size < 3 -> {
                        warnings.add("Line $lineNumber: Missing columns (found ${columns.size}, expected 3) - $columns")
                        continue
                    }
                    columns.size > 3 -> {
                        warnings.add("Line $lineNumber: Extra columns (found ${columns.size}, expected 3) - $columns")
                        continue
                    }
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

                val capacity = parseCapacity(capacityStr)
                if (capacity <= 0) {
                    warnings.add("Line $lineNumber: Invalid capacity value '$capacityStr' (must be positive number)")
                    continue
                }

                try {
                    val warehouse = Warehouse(
                        id = warehouseId,
                        location = location,
                        capacity = capacity
                    )
                    warehouses.add(warehouse)
                } catch (e: IllegalArgumentException) {
                    warnings.add("Line $lineNumber: ${e.message}")
                }
            }

        } catch (e: Exception) {
            warnings.add("Error reading file: ${e.message}")
        }

        return warehouses
    }

    private fun handleDelimiters(line: String): String {
        var processed = line.trimEnd(',')
        processed = processed.replace(Regex(",{2,}"), ",")
        return processed
    }

    private fun isValidWarehouseId(id: String): Boolean {
        return id.matches(Regex("^WH-\\d{3}$"))
    }

    private fun parseCapacity(value: String): Int {
        val trimmed = value.trim()
        return try {
            val capacity = trimmed.toInt()
            if (capacity > 0) capacity else -1
        } catch (e: NumberFormatException) {
            try {
                val numbers = trimmed.filter { it.isDigit() }
                if (numbers.isNotEmpty()) {
                    numbers.toInt()
                } else {
                    -1
                }
            } catch (ex: Exception) {
                -1
            }
        }
    }

    fun getWarnings(): List<String> = warnings.toList()

    fun printProcessingReport(warehouses: List<Warehouse>) {
        println("=".repeat(60))
        println(" WAREHOUSE PROCESSING REPORT")
        println("=".repeat(60))
        println(" Valid warehouses: ${warehouses.size}")
        println("  Warnings: ${warnings.size}")

        if (warnings.isNotEmpty()) {
            println("\n WARNINGS DETAILS:")
            warnings.forEachIndexed { index, warning ->
                println("  ${index + 1}. $warning")
            }
        }

        if (warehouses.isNotEmpty()) {
            println("\n VALID WAREHOUSES:")
            warehouses.forEach { warehouse ->
                println("  • ${warehouse.id} | Location: ${warehouse.location} | Capacity: ${warehouse.capacity}")
            }
        }
        println("=".repeat(60))
    }
}