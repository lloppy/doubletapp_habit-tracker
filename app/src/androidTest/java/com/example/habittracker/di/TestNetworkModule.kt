package com.example.habittracker.di

import com.example.data.remote.api.HabitsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
class TestNetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", AUTH_TOKEN)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): HabitsApiService =
        retrofit.create(HabitsApiService::class.java)


    companion object {
        private const val API_BASE_URL = "https://droid-test-server.doubletapp.ru/api/"
        private const val AUTH_TOKEN = "765a5153-2f3c-4482-afb7-6bdac5ecab55"
    }

}
