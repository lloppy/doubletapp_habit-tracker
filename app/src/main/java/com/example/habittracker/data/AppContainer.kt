package com.example.habittracker.data

import android.content.Context
import com.example.data.local.HabitsLocalRepository
import com.example.data.remote.api.HabitsApiService
import com.example.data.repository.HabitsRepository
import com.example.habittracker.data.repository.LanguageRepository
import com.example.data.repository.LanguageRepositoryImpl
import com.example.data.repository.HabitsRepositoryImpl
import com.example.habittracker.data.repository.ThemeRepository
import com.example.data.repository.ThemeRepositoryImpl
import com.example.data.remote.datasource.HabitsRemoteDataSource
import com.example.data.util.LanguageRepositoryProxy
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
        HabitsRepositoryImpl(
            local = HabitsLocalRepository(
                habitDao = OfflineDatabase.getDatabase(context).habitDao()
            ),
            remote = HabitsRemoteDataSource(retrofitService = retrofitService)
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