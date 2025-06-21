package com.roy.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roy.newsapp.presentation.article.ArticleScreen
import com.roy.newsapp.presentation.home.HomeScreen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Home){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable<Screen.Home> {
            HomeScreen(
                navController = navController,
            )
        }
        composable<Screen.Article> {
            ArticleScreen(
                navController = navController
            )
        }
    }
}