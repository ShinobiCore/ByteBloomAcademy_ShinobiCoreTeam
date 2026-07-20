package com.logistics.utils

import com.logistics.dataholder.Package

/**
 * Helper function to convert text-based priority into numerical values.
 * Higher numbers represent higher priorities to facilitate descending sorting.
 */
private fun getPriorityValue(priority: String): Int {
    return when (priority.uppercase()) {
        "URGENT" -> 3
        "STANDARD" -> 2
        "LOW" -> 1
        else -> 1 // Unknown priorities default to LOW as per requirements
    }
}

/**
 * Compares two packages to determine which one should appear first.
 * Sorting criteria: Priority first (descending), then Weight (descending).
 */
private fun isFirstGreater(p1: Package, p2: Package): Boolean {
    val priority1 = getPriorityValue(p1.priority)
    val priority2 = getPriorityValue(p2.priority)

    // 1. Compare by priority first
    if (priority1 > priority2) return true
    if (priority1 < priority2) return false

    // 2. If priorities are equal, fallback to comparing by weight
    return p1.weight > p2.weight
}

/**
 * A manual implementation of the Selection Sort algorithm.
 * Takes a list of packages and returns a new list sorted in descending order.
 *
 * @param packages The original list of valid packages.
 * @return A new list sorted by priority and weight.
 */
fun selectionSortPackages(packages: List<Package>): List<Package> {
    val sortedList = packages.toMutableList()
    val n = sortedList.size

    for (i in 0 until n - 1) {
        // Assume the current element is the largest
        var maxIndex = i

        for (j in i + 1 until n) {
            // We strictly use (>) instead of (>=).
            // This ensures "Stability" by preventing swaps between equal elements,
            // keeping them in their original read order.
            if (isFirstGreater(sortedList[j], sortedList[maxIndex])) {
                maxIndex = j
            }
        }

        // Swap the elements if a larger one was found in the remaining list
        if (maxIndex != i) {
            val temp = sortedList[maxIndex]
            sortedList[maxIndex] = sortedList[i]
            sortedList[i] = temp
        }
    }

    return sortedList
}