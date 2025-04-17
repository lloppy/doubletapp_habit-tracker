package com.example.habittracker.data.api.remote

import com.example.habittracker.model.model.HabitDoneResponse
import com.example.habittracker.model.model.HabitFetchResponse
import com.example.habittracker.model.model.HabitUid
import com.example.habittracker.model.model.HabitUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitsApiService {
    @GET("habit")
    suspend fun getHabits(): Response<List<HabitFetchResponse>>

    @PUT("habit")
    suspend fun updateHabit(
        @Body habit: HabitUpdateRequest
    ): Response<HabitUid>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(
        @Body habitUid: HabitUid
    ): Response<Unit>

    @POST("habit_done")
    suspend fun markDoneHabit(
        @Body habitDone: HabitDoneResponse
    ): Response<Unit>
}