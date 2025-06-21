package com.roy.newsapp.data.remote

import com.roy.newsapp.data.model.NewsRequest
import com.roy.newsapp.data.model.NewsResponse
import com.roy.newsapp.utils.networkRequestBlock
import com.roy.newsapp.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.encodedPath
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
) {

    suspend fun getTopHeadlines(newsRequest: NewsRequest): NetResult<NewsResponse> {
        return try {
            client.networkRequestBlock(
                HttpRequestBuilder().apply {
                    url {
                        url(Constants.Api.BASE_URL)
                        encodedPath += "top-headlines"
                        parameters.append("country", newsRequest.country)
                        parameters.append("page", newsRequest.page.toString())
                        parameters.append("pageSize", newsRequest.pageSize.toString())
                        parameters.append("apiKey", newsRequest.apiKey)
                    }
                }
            )
        } catch (e: Exception) {
            NetResult.Failure(e)
        }
    }
}