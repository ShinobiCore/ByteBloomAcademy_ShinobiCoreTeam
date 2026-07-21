package com.logistics.utils

import com.logistics.dataholder.Package

/**
 * Extension function to convert text-based priority into numerical values.
 */
private fun String.toPriorityValue(): Int = when (this.uppercase()) {
    "URGENT" -> 3
    "STANDARD" -> 2
    "LOW" -> 1
    else -> 1
}

/**
 * Compares two packages. Returns true if the first package has higher priority,
 * or same priority but higher weight.
 */
private fun isFirstGreater(p1: Package, p2: Package): Boolean {
    val priority1 = p1.priority.toPriorityValue()
    val priority2 = p2.priority.toPriorityValue()

    return when {
        priority1 > priority2 -> true
        priority1 < priority2 -> false
        else -> p1.weight > p2.weight
    }
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
        var maxIndex = i

        for (j in i + 1 until n) {
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