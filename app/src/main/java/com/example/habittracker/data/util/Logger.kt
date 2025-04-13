package com.example.habittracker.data.util

import android.util.Log

abstract class Logger(private val tag: String) {
    fun log(message: String) = Log.d(tag, "✅ $message")
    fun logInfo(message: String) = Log.d(tag, "ℹ\uFE0F $message")
    fun logError(message: String, error: Throwable? = null) = Log.e(tag, "❌ $message", error)
}