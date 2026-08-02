package com.logistics.sorter

import com.logistics.data.raw.PackageRaw

private fun isFirstGreater(p1: PackageRaw, p2: PackageRaw): Boolean {
    val priority1 = p1.priority
    val priority2 = p2.priority

    return when {
        priority1 > priority2 -> true
        priority1 < priority2 -> false
        else -> p1.weight > p2.weight
    }
}

fun selectionSortPackages(packages: List<PackageRaw>): List<PackageRaw> {
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