package com.roy.newsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsRequest (
    val country: String,
    val page: Int,
    val pageSize: Int,
    val apiKey: String
)