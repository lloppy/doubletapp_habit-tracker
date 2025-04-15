package com.example.habittracker.data.api

import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitDone
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitsApiService {
    @GET("habit")
    suspend fun getHabits(
        @Header("Authorization") token: String = TOKEN
    ): List<Habit>

    @PUT("habit")
    suspend fun updateHabit(
        @Header("Authorization") token: String = TOKEN,
        @Body habit: Habit
    )

    @DELETE("habit")
    suspend fun deleteHabit(
        @Header("Authorization") token: String = TOKEN,
        @Body habitUid: String
    )

    @POST("habit_done")
    suspend fun markDoneHabit(
        @Header("Authorization") token: String = TOKEN,
        @Body habitDone: HabitDone
    )

    companion object {
        const val TOKEN = "5efa4c27-2f41-4ec9-8983-002e0961c903"
    }


}