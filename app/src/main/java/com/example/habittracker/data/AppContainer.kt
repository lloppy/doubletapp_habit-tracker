package com.example.habittracker.data

import android.content.Context
import android.util.Log
import com.example.habittracker.data.api.remote.HabitsApiService
import com.example.habittracker.data.repository.LanguageRepository
import com.example.habittracker.data.repository.LanguageRepositoryImpl
import com.example.habittracker.data.repository.SharedHabitsRepository
import com.example.habittracker.data.repository.ThemeRepository
import com.example.habittracker.data.repository.ThemeRepositoryImpl
import com.example.habittracker.data.repository.local.HabitsRepository
import com.example.habittracker.data.repository.local.OfflineHabitsRepository
import com.example.habittracker.data.repository.remote.OnlineHabitsRepository
import com.example.habittracker.data.util.LanguageRepositoryProxy
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val habitsRepository: HabitsRepository
    val themeRepository: ThemeRepository
    val languageRepository: LanguageRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", AUTH_TOKEN)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
            Log.d("API", "Requesting: ${request.url}")
            chain.proceed(request)
        }
        .build()


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val retrofitService: HabitsApiService by lazy {
        retrofit.create(HabitsApiService::class.java)
    }

    override val habitsRepository: HabitsRepository by lazy {
        SharedHabitsRepository(
            local = OfflineHabitsRepository(
                habitDao = OfflineDatabase.getDatabase(context).habitDao()
            ),
            remote = OnlineHabitsRepository(retrofitService = retrofitService)
        )
    }

    override val themeRepository: ThemeRepository = ThemeRepositoryImpl(context)

    override val languageRepository: LanguageRepository = LanguageRepositoryProxy(
        languageRepository = LanguageRepositoryImpl(context)
    )

    companion object {
        private const val API_BASE_URL = "https://droid-test-server.doubletapp.ru/api/"
        private const val AUTH_TOKEN = "765a5153-2f3c-4482-afb7-6bdac5ecab55"
    }
}