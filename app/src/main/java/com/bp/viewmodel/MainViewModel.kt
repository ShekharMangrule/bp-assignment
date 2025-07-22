package com.bp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bp.data.ApiResponse
import com.bp.data.BpQuery
import com.bp.data.BpStation
import com.bp.data.UiState
import com.bp.repository.MockMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mockMainRepository: MockMainRepository
) : ViewModel() {

    private val _bpQuery = MutableStateFlow(BpQuery())

    val bpQuery: StateFlow<BpQuery> = _bpQuery.asStateFlow()

    private val _bpStations = MutableStateFlow<UiState<List<BpStation>>>(UiState())
    val bpStations = _bpStations.asStateFlow()

    init {
        viewModelScope.launch {
            _bpQuery.debounce(200)
                .distinctUntilChanged()
                .flatMapLatest {
                    mockMainRepository.getStations(it)
                }
                .collectLatest { apiResponse ->
                    when (apiResponse) {

                        ApiResponse.Loading -> {
                            _bpStations.value = _bpStations.value.copy(
                                isLoading = true,
                            )
                        }

                        is ApiResponse.Success -> {
                            _bpStations.value = _bpStations.value.copy(
                                isLoading = false,
                                isSuccess = true,
                                data = apiResponse.data
                            )
                        }

                        is ApiResponse.Error -> {
                            _bpStations.value = _bpStations.value.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = apiResponse.message
                            )
                        }
                    }
                }
        }
    }

    fun changeBpQuery(query: BpQuery) {
        _bpQuery.value = query
    }
}