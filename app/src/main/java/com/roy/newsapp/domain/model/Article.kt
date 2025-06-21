package com.roy.newsapp.domain.model

import com.roy.newsapp.data.model.ArticleDto
import java.io.Serializable

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
): Serializable

fun ArticleDto.toArticle(): Article {
    return Article(
        source = Source(
            id = this.source.id,
            name = this.source.name
        ),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}