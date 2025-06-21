package com.roy.newsapp.di

import com.roy.newsapp.data.remote.ApiServiceImpl
import com.roy.newsapp.domain.repository.NewsRepository
import com.roy.newsapp.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object NewsModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
        }
    }

    @Provides
    @Singleton
    fun provideApiService(
        httpClient: HttpClient,
    ): ApiServiceImpl {
        return ApiServiceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiServiceImpl: ApiServiceImpl,
    ): NewsRepository{
        return NewsRepositoryImpl(apiServiceImpl)
    }
}