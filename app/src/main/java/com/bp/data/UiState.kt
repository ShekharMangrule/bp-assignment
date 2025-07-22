package com.bp.data

data class UiState<T>(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val data: T? = null
)