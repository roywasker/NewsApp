package com.roy.newsapp.presentation.home

data class NewsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val isEndReached: Boolean = false
)
