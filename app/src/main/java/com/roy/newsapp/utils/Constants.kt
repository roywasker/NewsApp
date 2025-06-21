package com.roy.newsapp.utils

import com.roy.newsapp.R

object Constants {

    object Api {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "a027b176a97b4917be9e2430d03cdf77"
    }

    object Text {
        const val HomeTopBarTitle = "News"
        const val ArticleTopBarTitle = "Article"
        const val ArticleButton = "Read More"
        const val EmptyArticleList = "No articles to display"
        const val TryAgainButton = "Try Again"
        const val GoBackButton = "Go Back"
        const val ArticleUnavailable = "Article currently unavailable"
        const val FailedErrorMessage = "Oops! Something went wrong."
        const val NetworkErrorMessage = "You're offline. Check your connection."

    }

    object Image{
        val articleImage = R.drawable.article
    }

}