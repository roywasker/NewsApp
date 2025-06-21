package com.roy.newsapp.data.repository

import com.roy.newsapp.data.model.NewsRequest
import com.roy.newsapp.data.model.NewsResponse
import com.roy.newsapp.data.remote.ApiServiceImpl
import com.roy.newsapp.data.remote.NetResult
import com.roy.newsapp.domain.model.Article
import com.roy.newsapp.domain.model.toArticle
import com.roy.newsapp.domain.repository.NewsRepository
import com.roy.newsapp.utils.Constants
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiServiceImpl,
): NewsRepository {

    override suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        onNetworkFailure: suspend (NetResult.NetworkFailure<Exception>) -> Unit,
        onFailure: suspend (NetResult.Failure<Exception>) -> Unit,
        onSuccess: suspend (List<Article>) -> Unit
    ){
        val result = getTopHeadlinesResult(
            page = page,
            pageSize = pageSize
        )
        when(result){
            is NetResult.Success -> {
                val articles = result.response.articles.map {
                    it.toArticle()
                }
                onSuccess.invoke(articles)
            }
            is NetResult.Failure<*> -> {
                onFailure.invoke(NetResult.Failure(result.generalError))
            }
            is NetResult.NetworkFailure<*> -> {
                onNetworkFailure.invoke(NetResult.NetworkFailure(result.networkError))
            }
        }
    }

    override suspend fun getTopHeadlinesResult(page: Int, pageSize: Int): NetResult<NewsResponse> {
        return apiService.getTopHeadlines(
            newsRequest = NewsRequest(
                country = "us",
                page = page,
                pageSize = pageSize,
                apiKey = Constants.Api.API_KEY
            )
        )
    }
}