package com.example.habittracker.data

import android.content.Context
import com.example.habittracker.data.api.HabitsApiService
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.data.repository.LanguageRepository
import com.example.habittracker.data.repository.LanguageRepositoryImpl
import com.example.habittracker.data.repository.NetworkHabitsRepository
import com.example.habittracker.data.repository.OfflineHabitsRepository
import com.example.habittracker.data.repository.OnlineHabitsRepository
import com.example.habittracker.data.repository.ThemeRepository
import com.example.habittracker.data.repository.ThemeRepositoryImpl
import com.example.habittracker.data.util.LanguageRepositoryProxy
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val offlineHabitsRepository: HabitsRepository
    val onlineHabitsRepository: NetworkHabitsRepository
    val themeRepository: ThemeRepository
    val languageRepository: LanguageRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://droid-test-server.doubletapp.ru/"

    private val mediaType = "application/json"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory(mediaType.toMediaType()))
        .build()

    private val retrofitService: HabitsApiService by lazy {
        retrofit.create(HabitsApiService::class.java)
    }

    override val onlineHabitsRepository: NetworkHabitsRepository by lazy {
        OnlineHabitsRepository(retrofitService)
    }

    override val offlineHabitsRepository: HabitsRepository by lazy {
        OfflineHabitsRepository(habitDao = OfflineDatabase.getDatabase(context).habitDao())
    }

    override val themeRepository: ThemeRepository = ThemeRepositoryImpl(context)

    override val languageRepository: LanguageRepository = LanguageRepositoryProxy(
        languageRepository = LanguageRepositoryImpl(context)
    )
}