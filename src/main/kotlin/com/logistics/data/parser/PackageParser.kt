package com.logistics.data.parser

import com.logistics.data.raw.PackageRaw
import com.logistics.domain.model.Priority
import com.logistics.utils.CsvUtils

object PackageParser {

    fun parsePackageFile(filePath: String): ParseResult<PackageRaw> {
        val rawLines = CsvUtils.readLinesWithoutHeader(filePath)
        val packages = mutableListOf<PackageRaw>()
        val warnings = mutableListOf<String>()

        for ((index, line) in rawLines.withIndex()) {
            val lineNumber = index + 2
            processLine(line, lineNumber, packages, warnings)
        }

        return ParseResult(packages, warnings)
    }

    private fun processLine(
        line: String,
        lineNumber: Int,
        packages: MutableList<PackageRaw>,
        warnings: MutableList<String>
    ) {
        if (line.isBlank()) return

        val tokens = CsvUtils.splitAndTrim(line)
        if (isColumnMismatch(tokens)) {
            warnings.add("Line $lineNumber: Missing required columns or destinationHubId missing.")
            return
        }

        val pkg = buildPackageFromTokens(tokens)
        packages.add(pkg)
    }

    private fun isColumnMismatch(tokens: List<String>): Boolean {
        return tokens.size < 5 || tokens[0].isEmpty() || tokens[1].isEmpty()
    }

    private fun buildPackageFromTokens(tokens: List<String>): PackageRaw {
        val packageId = tokens[0]
        val weight = CsvUtils.parseSafeDouble(tokens[1])
        val originWarehouseId = tokens[2]
        val destinationWarehouseId = tokens[3]
        val priority = Priority.fromText(tokens[4])

        return PackageRaw(
            id = packageId,
            weight = weight,
            priority = priority,
            originWarehouseId = originWarehouseId,
            destinationWarehouseId = destinationWarehouseId
        )
    }

    private fun parsePriority(rawPriority: String): Priority {
        return when (rawPriority.uppercase()) {
            "URGENT" -> Priority.URGENT
            "STANDARD" -> Priority.STANDARD
            else -> Priority.LOW
        }
    }
}