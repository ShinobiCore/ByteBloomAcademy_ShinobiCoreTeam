package com.logistics

import com.logistics.dataholder.Package
import com.logistics.parsers.parseVehicleFile
import com.logistics.utils.selectionSortPackages

fun main() {
    // Mock Packages Data
    val validPackages = listOf(
        Package("PKG-210", 9.8, "STANDARD", "WH-02"),
        Package("PKG-101", 12.5, "URGENT", "WH-03"),
        Package("PKG-055", 11.0, "URGENT", "WH-01")
    )
    val packageWarnings = listOf("Warning at line 4: Invalid weight. Skipped.")

    // ==========================================
    // 2. Parse Actual Fleet Data (Your Task)
    // ==========================================
    val (validFleet, fleetWarnings) = parseVehicleFile("src/main/resources/fleet.csv")


    // ==========================================
    // 3. Print Required Statistics
    // ==========================================
    println("=====================================")
    println("== Statistics ==")
    println("=====================================")
    println("Packages parsed: ${validPackages.size}")
    println("Fleet parsed: ${validFleet.size}")
    println()
    // ==========================================
    // 4. Sort Packages and Print Top 3
    // ==========================================
    val sortedPackages = selectionSortPackages(validPackages)

    println("=== Top 3 ===")
    sortedPackages.take(3).forEachIndexed { index, pkg ->
        // Format exactly as requested
        println("${index + 1}. ${pkg.packageId}")
        println(pkg.priority)
        println("Weight: ${pkg.weight}")
        println("Destination: ${pkg.destinationHub}")
    }


    // ==========================================
    // 5. Interactive Data Viewer Menu
    // ==========================================
    while (true) {
        println("\n=============================")
        println("     DATA VIEWER MENU")
        println("=============================")
        println("1. Packages")
        println("2. Warehouses")
        println("3. Routes")
        println("4. Fleet")
        println("0. Exit Program")
        print("Select a table to view (0-4): ")

        // Read user input safely
        val tableChoice = readlnOrNull()?.toIntOrNull()

        if (tableChoice == 0) {
            println("Exiting program. Goodbye!")
            break
        }

        if (tableChoice !in 1..4) {
            println("Invalid choice. Please enter a number between 0 and 4.")
            continue
        }

        // Ask for data type (Valid or Invalid/Warnings)
        println("\n1. View Valid Data")
        println("2. View Invalid Data / Warnings")
        print("Select data type (1-2): ")

        val typeChoice = readlnOrNull()?.toIntOrNull()

        if (typeChoice !in 1..2) {
            println("Invalid choice. Returning to main menu.")
            continue
        }

        println("\n--- Results ---")

        // Display data based on user selection
        when (tableChoice) {
            1 -> {
                if (typeChoice == 1) validPackages.forEach { println(it) }
                else displayWarnings(packageWarnings)
            }
            2 -> {
            }
            3 -> {
            }
            4 -> {
                if (typeChoice == 1) validFleet.forEach { println(it) }
                else displayWarnings(fleetWarnings)
            }
        }
    }
}

/**
 * Helper function to print warnings cleanly.
 */
fun displayWarnings(warnings: List<String>) {
    if (warnings.isEmpty()) {
        println("No errors or warnings found. All data is clean!")
    } else {
        warnings.forEach { println(it) }
    }
}