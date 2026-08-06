package com.logistics.sorter

import com.logistics.domain.model.Package

object QuickSorter {
    fun sortPackagesByWeightDescending(packages: MutableList<Package>) {
        quickSortByWeightDescending(packages, 0, packages.lastIndex)
    }

    private fun quickSortByWeightDescending(packages: MutableList<Package>, lowIndex: Int, highIndex: Int) {
        if (lowIndex >= highIndex) {
            return
        }

        val pivotWeight = packages[(lowIndex + highIndex) / 2].weight
        val heavier = mutableListOf<Package>()
        val equal = mutableListOf<Package>()
        val lighter = mutableListOf<Package>()

        for (index in lowIndex..highIndex) {
            val packageItem = packages[index]
            when {
                packageItem.weight > pivotWeight -> heavier.add(packageItem)
                packageItem.weight < pivotWeight -> lighter.add(packageItem)
                else -> equal.add(packageItem)
            }
        }

        var writeIndex = lowIndex
        for (packageItem in heavier) {
            packages[writeIndex] = packageItem
            writeIndex++
        }
        for (packageItem in equal) {
            packages[writeIndex] = packageItem
            writeIndex++
        }
        for (packageItem in lighter) {
            packages[writeIndex] = packageItem
            writeIndex++
        }

        val heavierHighIndex = lowIndex + heavier.size - 1
        val lighterLowIndex = highIndex - lighter.size + 1
        quickSortByWeightDescending(packages, lowIndex, heavierHighIndex)
        quickSortByWeightDescending(packages, lighterLowIndex, highIndex)
    }
}
