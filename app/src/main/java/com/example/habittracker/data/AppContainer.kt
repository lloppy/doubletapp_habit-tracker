package com.example.habittracker.data

import android.content.Context
import com.example.habittracker.data.api.HabitsApiService
import com.example.habittracker.data.repository.HabitsRepository
import com.example.habittracker.data.repository.LanguageRepository
import com.example.habittracker.data.repository.LanguageRepositoryImpl
import com.example.habittracker.data.repository.OfflineHabitsRepository
import com.example.habittracker.data.repository.OnlineHabitsRepository
import com.example.habittracker.data.repository.ThemeRepository
import com.example.habittracker.data.repository.ThemeRepositoryImpl
import com.example.habittracker.data.util.LanguageRepositoryProxy
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val offlineHabitsRepository: HabitsRepository
    val onlineHabitsRepository: HabitsRepository
    val themeRepository: ThemeRepository
    val languageRepository: LanguageRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", AUTH_TOKEN)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory(MEDIA_TYPE.toMediaType()))
        .build()

    private val retrofitService: HabitsApiService by lazy {
        retrofit.create(HabitsApiService::class.java)
    }

    override val onlineHabitsRepository: HabitsRepository by lazy {
        OnlineHabitsRepository(retrofitService)
    }

    override val offlineHabitsRepository: HabitsRepository by lazy {
        OfflineHabitsRepository(habitDao = OfflineDatabase.getDatabase(context).habitDao())
    }

    override val themeRepository: ThemeRepository = ThemeRepositoryImpl(context)

    override val languageRepository: LanguageRepository = LanguageRepositoryProxy(
        languageRepository = LanguageRepositoryImpl(context)
    )

    companion object {
        private const val BASE_URL = "https://droid-test-server.doubletapp.ru/"
        private const val AUTH_TOKEN = "5efa4c27-2f41-4ec9-8983-002e0961c903"
        private const val MEDIA_TYPE = "application/json"
    }
}