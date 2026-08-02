package com.logistics.data.parser

data class ParseResult<T>(
    val rows: List<T>,
    val warnings: List<String>
)
