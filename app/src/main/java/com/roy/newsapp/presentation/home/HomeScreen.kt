package com.roy.newsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.roy.newsapp.presentation.components.ArticleItem
import com.roy.newsapp.presentation.components.ErrorCard
import com.roy.newsapp.presentation.navigation.Screen
import com.roy.newsapp.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
){

    val articles = viewModel.articles
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val listState = rememberLazyListState()

    val isRefreshing = state.isLoading && articles.isNotEmpty()

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisible to totalItems
        }
            .distinctUntilChanged()
            .collectLatest { (lastVisible, totalItems) ->
                if (lastVisible != null && lastVisible >= totalItems - 3) {
                    viewModel.fetchArticle()
                }
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = Constants.Text.HomeTopBarTitle,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
            )
        }
    ) { padding ->

        when{
            state.isLoading && articles.isEmpty() ->{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .offset(y = (-30).dp),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(
                        color = Color.Black.copy(0.8f)
                    )
                }
            }

            !state.isLoading && articles.isEmpty() && state.error == null ->{

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .offset(y = (-30).dp),
                        text = Constants.Text.EmptyArticleList,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            state.error != null && articles.isEmpty() ->{
                ErrorCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 12.dp)
                        .offset(y = (-40).dp),
                    message = state.error,
                    buttonText = Constants.Text.TryAgainButton,
                    onClick = viewModel::refresh
                )
            }

            else ->{
                PullToRefreshBox(
                    state = rememberPullToRefreshState(),
                    onRefresh = {
                        viewModel.refresh()
                    },
                    isRefreshing = isRefreshing
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        state = listState
                    ) {
                        itemsIndexed(articles) { index, article ->

                            ArticleItem(
                                article = article,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "article",
                                        article
                                    )
                                    navController.navigate(Screen.Article)
                                }
                            )
                        }

                        if (state.isLoading && articles.isNotEmpty()) {
                            item {
                                CircularProgressIndicator(
                                    color = Color.Black.copy(0.8f)
                                )
                            }
                        }

                        if (state.error != null && articles.isNotEmpty()) {
                            item {
                                ErrorCard(
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    message = state.error,
                                    buttonText = Constants.Text.TryAgainButton,
                                    onClick = viewModel::fetchArticle
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}