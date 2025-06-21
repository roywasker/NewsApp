package com.roy.newsapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roy.newsapp.domain.model.Article
import com.roy.newsapp.domain.repository.NewsRepository
import com.roy.newsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
): ViewModel()  {

    var articles by mutableStateOf<List<Article>>(emptyList())
        private set

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        fetchArticle()
    }

    fun refresh() {
        articles = emptyList()
        _uiState.value = uiState.value.copy(
            currentPage = 1,
            error = null,
            isEndReached = false,
            isLoading = false
        )
        fetchArticle()
    }

    fun fetchArticle (){

        if (uiState.value.isEndReached || uiState.value.isLoading) return

        _uiState.value = uiState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            newsRepository.getTopHeadlines(
                page = uiState.value.currentPage,
                pageSize = 20,
                onSuccess = {
                    articles = articles + it
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        currentPage = uiState.value.currentPage + 1,
                        isEndReached = it.isEmpty()
                    )
                },
                onFailure = {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = Constants.Text.FailedErrorMessage
                    )
                },
                onNetworkFailure = {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = Constants.Text.NetworkErrorMessage
                    )
                }
            )
        }
    }
}