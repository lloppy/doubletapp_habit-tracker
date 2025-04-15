package com.example.habittracker.data.api

import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitDone
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitsApiService {
    @GET("habit")
    suspend fun getHabits(): List<Habit>

    @PUT("habit")
    suspend fun updateHabit(
        @Body habit: Habit
    )

    @DELETE("habit")
    suspend fun deleteHabit(
        @Body habitUid: String
    )

    @POST("habit_done")
    suspend fun markDoneHabit(
        @Body habitDone: HabitDone
    )
}