package com.example.data.repository

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import com.example.domain.repository.LanguageRepository
import com.example.model.Language
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val context: Context
) : LanguageRepository {

    override fun getCurrentLanguage(): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                ?.applicationLocales
                ?.get(0)
        } else {
            AppCompatDelegate.getApplicationLocales().get(0)
        }
        return locale?.language ?: getDefaultLanguageCode()
    }

    override fun setAppLanguage(languageCode: String): Result<Unit> = runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageCode)
        } else {
            // AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
            // need to apply theme AppCompat but i use material compose
            throw UnsupportedOperationException("Language change requires API 33+ (Tiramisu)")
        }
    }

    override fun getAvailableLanguages(): List<Language> = appLanguages

    private fun getDefaultLanguageCode(): String {
        return appLanguages.first().code
    }

    companion object {
        val appLanguages = listOf(
            Language("en", "English"),
            Language("es", "Español"),
            Language("ru", "Русский")
        )
    }
}
