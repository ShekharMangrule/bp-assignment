package com.bp.data

data class BpQuery(
    val radius: Double = 15.0,
    val is24Available: Boolean = false,
    val bpCardAcceptance: Boolean = false,
    val isServeHotFood: Boolean = true,
    val hasConvenienceStore: Boolean = true
)
