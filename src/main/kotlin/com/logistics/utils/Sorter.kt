
package com.logistics.utils

import com.logistics.dataholder.Package
import com.logistics.dataholder.Priority

private fun Priority.toPriorityValue(): Int = when (this) {
    Priority.URGENT -> 3
    Priority.STANDARD -> 2
    Priority.LOW -> 1
}

private fun isFirstGreater(p1: Package, p2: Package): Boolean {
    val priority1 = p1.priority.toPriorityValue()
    val priority2 = p2.priority.toPriorityValue()

    return when {
        priority1 > priority2 -> true
        priority1 < priority2 -> false
        else -> p1.weight > p2.weight
    }
}
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

        if (maxIndex != i) {
            val temp = sortedList[maxIndex]
            sortedList[maxIndex] = sortedList[i]
            sortedList[i] = temp
        }
    }

    return sortedList
}