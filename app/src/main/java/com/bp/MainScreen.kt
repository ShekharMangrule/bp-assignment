package com.bp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bp.data.BpStation
import com.bp.repository.MockMainRepository
import com.bp.viewmodel.MainViewModel
import com.bp.viewmodel.MainViewModelFactory

@Composable
fun HomeScreen() {
    val mockMainRepository = MockMainRepository()

    var value by remember { mutableStateOf("10") }

    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(mockMainRepository)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.radius)
        )

        TextField(
            value = value,
            onValueChange = { value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }

    val serviceStations by viewModel.bpStations.collectAsStateWithLifecycle()

    val mContext = LocalContext.current
    when {
        serviceStations.isLoading -> {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
        }

        serviceStations.isSuccess -> {
            HomeScreenContent(
                stations = serviceStations.data ?: emptyList()
            )
        }

        serviceStations.isError -> {
            Toast.makeText(mContext, serviceStations.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(value) {
        val distance = if (value.isEmpty()) 0.0 else value.toDouble()
        val query = viewModel.bpQuery.value.copy(radius = distance)
        viewModel.changeBpQuery(query)
    }
}

@Composable
fun HomeScreenContent(
    stations: List<BpStation>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (stations.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.no_station_found_within_radius),

                )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(
                    items = stations,
                    key = { it.id }
                ) { serviceStation ->
                    BPStationItem(item = serviceStation)
                }
            }
        }
    }
}

@Composable
fun BPStationItem(modifier: Modifier = Modifier, item: BpStation) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Hardcoded Leading Content
            Image(
                painter = painterResource(id = R.drawable.ic_station), // Example icon
                contentDescription = stringResource(id = R.string.app_name), // Example description
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Hardcoded Main Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.address,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )

                Text(
                    text = ("${item.distance} mi"),
                    style = MaterialTheme.typography.labelSmall
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

