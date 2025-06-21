package com.roy.newsapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home: Screen()

    @Serializable
    data object Article: Screen()
}