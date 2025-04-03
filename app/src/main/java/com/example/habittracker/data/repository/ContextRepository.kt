package com.example.habittracker.data.repository

import android.content.Context

interface ContextRepository {
    fun getContext(): Context
    fun getString(resId: Int): String
}

class ContextRepositoryImpl(private val appContext: Context) : ContextRepository {

    override fun getContext(): Context = appContext

    override fun getString(resId: Int): String = appContext.getString(resId)
}