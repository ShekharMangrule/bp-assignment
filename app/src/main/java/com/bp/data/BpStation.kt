package com.bp.data

data class BpStation(
    val id: String,
    val name: String,
    val address: String,
    val contactNumber: String,
    val distance: Double,
    val is24Available: Boolean,
    val isStoreAvailable: Boolean,
    val isServeHotFoodAvailable: Boolean,
    val bpCardAcceptance: Boolean,
    val latitude: Double,
    val longitude: Double
)
