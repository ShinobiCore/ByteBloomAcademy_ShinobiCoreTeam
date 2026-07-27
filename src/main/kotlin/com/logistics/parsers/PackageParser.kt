package com.logistics.parsers

import com.logistics.dataholder.PackageParseResult
import com.logistics.dataholder.PackageRaw
import com.logistics.dataholder.Priority
import com.logistics.utils.CsvUtils

object PackageParser {

    fun parsePackageFile(filePath: String): PackageParseResult {
        val rawLines = CsvUtils.readLinesWithoutHeader(filePath)
        val packages = mutableListOf<PackageRaw>()
        val warnings = mutableListOf<String>()

        for ((index, line) in rawLines.withIndex()) {
            val lineNumber = index + 2
            processLine(line, lineNumber, packages, warnings)
        }

        return PackageParseResult(packages, warnings)
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
        return tokens.size < 4 || tokens[0].isEmpty() || tokens[1].isEmpty()
    }

    private fun buildPackageFromTokens(tokens: List<String>): PackageRaw {
        val packageId = tokens[0]
        val hubId = tokens[1]
        val weight = CsvUtils.parseSafeDouble(tokens[2])
        val priority = parsePriority(tokens[3])

        return PackageRaw(packageId, hubId, weight, priority)
    }

    private fun parsePriority(rawPriority: String): Priority {
        return when (rawPriority.uppercase()) {
            "URGENT" -> Priority.URGENT
            "STANDARD" -> Priority.STANDARD
            else -> Priority.LOW
        }
    }
}