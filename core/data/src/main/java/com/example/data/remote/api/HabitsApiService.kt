package com.example.data.remote.api

import com.example.data.remote.model.HabitDoneResponse
import com.example.data.remote.model.HabitFetchResponse
import com.example.data.remote.model.HabitUid
import com.example.data.remote.model.HabitUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitsApiService {

    @GET("habit")
    suspend fun getHabits(): List<HabitFetchResponse>

    @PUT("habit")
    suspend fun updateHabit(@Body habit: HabitUpdateRequest): HabitUid

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habitUid: HabitUid)

    @POST("habit_done")
    suspend fun markDoneHabit(@Body habitDone: HabitDoneResponse)

}