package com.roy.newsapp.domain.repository

import com.roy.newsapp.data.model.NewsResponse
import com.roy.newsapp.data.remote.NetResult
import com.roy.newsapp.domain.model.Article

interface NewsRepository {

    suspend fun getTopHeadlines(
        page: Int,
        pageSize: Int,
        onNetworkFailure: suspend (NetResult.NetworkFailure<Exception>) -> Unit = {},
        onFailure: suspend (NetResult.Failure<Exception>) -> Unit = {},
        onSuccess: suspend (List<Article>) -> Unit = {}
    )

    suspend fun getTopHeadlinesResult(
        page: Int,
        pageSize: Int
    ): NetResult<NewsResponse>
}