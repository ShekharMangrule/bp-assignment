package com.bp.repository

import com.bp.data.ApiResponse
import com.bp.data.BpQuery
import com.bp.data.BpStation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MockMainRepository {

    // We can easily replace mock data to actual api
    fun getStations(query: BpQuery) = flow {
        emit(ApiResponse.Loading)
        kotlinx.coroutines.delay(1000)
        val stations = bpStationList
            .asSequence()
            .filter { station ->
                station.distance <= query.radius && station.is24Available == query.is24Available
            }
        emit(ApiResponse.Success(stations.toList()))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(ApiResponse.Error(it.message ?: "Something went wrong, please try again later"))
        }
}


val bpStationList = listOf(
    BpStation(
        id = "CA001",
        name = "BP Los Angeles Downtown",
        address = "123 Main St, Los Angeles, CA 90001",
        contactNumber = "(213) 555-1212",
        distance = 2.5,
        is24Available = true,
        isStoreAvailable = true,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = true,
        latitude = 34.052235,
        longitude = -118.243683
    ),
    BpStation(
        id = "CA002",
        name = "BP San Francisco Bayview",
        address = "456 Oak Ave, San Francisco, CA 94124",
        contactNumber = "(415) 555-3434",
        distance = 5.1,
        is24Available = false,
        isStoreAvailable = true,
        isServeHotFoodAvailable = true,
        bpCardAcceptance = true,
        latitude = 37.731945,
        longitude = -122.385254
    ),
    BpStation(
        id = "CA003",
        name = "BP San Diego Gaslamp",
        address = "789 Pine Rd, San Diego, CA 92101",
        contactNumber = "(619) 555-5656",
        distance = 1.0,
        is24Available = true,
        isStoreAvailable = true,
        isServeHotFoodAvailable = true,
        bpCardAcceptance = true,
        latitude = 32.710000,
        longitude = -117.162000
    ),
    BpStation(
        id = "CA004",
        name = "BP Sacramento Midtown",
        address = "101 Maple Ln, Sacramento, CA 95811",
        contactNumber = "(916) 555-7878",
        distance = 3.8,
        is24Available = true,
        isStoreAvailable = false,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = true,
        latitude = 38.575764,
        longitude = -121.478851
    ),
    BpStation(
        id = "CA005",
        name = "BP Fresno North",
        address = "222 Cedar Ave, Fresno, CA 93720",
        contactNumber = "(559) 555-9090",
        distance = 7.2,
        is24Available = false,
        isStoreAvailable = true,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = false,
        latitude = 36.840248,
        longitude = -119.776688
    ),
    BpStation(
        id = "CA006",
        name = "BP Long Beach Harbor",
        address = "333 Ocean Blvd, Long Beach, CA 90802",
        contactNumber = "(562) 555-1122",
        distance = 1.5,
        is24Available = true,
        isStoreAvailable = true,
        isServeHotFoodAvailable = true,
        bpCardAcceptance = true,
        latitude = 33.766950,
        longitude = -118.192660
    ),
    BpStation(
        id = "CA007",
        name = "BP Oakland Jack London",
        address = "444 Embarcadero West, Oakland, CA 94607",
        contactNumber = "(510) 555-3344",
        distance = 4.0,
        is24Available = true,
        isStoreAvailable = true,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = true,
        latitude = 37.795000,
        longitude = -122.278000
    ),
    BpStation(
        id = "CA008",
        name = "BP Anaheim Resort Area",
        address = "555 Harbor Blvd, Anaheim, CA 92802",
        contactNumber = "(714) 555-5566",
        distance = 0.5,
        is24Available = true,
        isStoreAvailable = true,
        isServeHotFoodAvailable = true,
        bpCardAcceptance = true,
        latitude = 33.809000,
        longitude = -117.919000
    ),
    BpStation(
        id = "CA009",
        name = "BP Santa Monica Pier",
        address = "666 Pacific Coast Hwy, Santa Monica, CA 90401",
        contactNumber = "(310) 555-7788",
        distance = 0.2,
        is24Available = false,
        isStoreAvailable = true,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = true,
        latitude = 34.008000,
        longitude = -118.495000
    ),
    BpStation(
        id = "CA010",
        name = "BP Bakersfield South",
        address = "777 Union Ave, Bakersfield, CA 93307",
        contactNumber = "(661) 555-9900",
        distance = 6.3,
        is24Available = true,
        isStoreAvailable = false,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = true,
        latitude = 35.351000,
        longitude = -118.995000
    ),
    BpStation(
        id = "CA011",
        name = "BP Riverside Downtown",
        address = "888 Mission Inn Ave, Riverside, CA 92501",
        contactNumber = "(951) 555-1230",
        distance = 2.1,
        is24Available = true,
        isStoreAvailable = true,
        isServeHotFoodAvailable = true,
        bpCardAcceptance = false,
        latitude = 33.980000,
        longitude = -117.375000
    ),
    BpStation(
        id = "CA012",
        name = "BP Stockton Waterfront",
        address = "999 Weber Ave, Stockton, CA 95202",
        contactNumber = "(209) 555-4567",
        distance = 3.5,
        is24Available = false,
        isStoreAvailable = true,
        isServeHotFoodAvailable = false,
        bpCardAcceptance = true,
        latitude = 37.957000,
        longitude = -121.288000
    )
)
