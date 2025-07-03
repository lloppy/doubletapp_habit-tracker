package com.example.data.remote.api

import com.example.data.remote.model.HabitDoneResponseDto
import com.example.data.remote.model.HabitFetchResponseDto
import com.example.data.remote.model.HabitUidDto
import com.example.data.remote.model.HabitUpdateRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface HabitsApiService {

    @GET("habit")
    suspend fun getHabits(): List<HabitFetchResponseDto>

    @PUT("habit")
    suspend fun updateHabit(@Body habitDto: HabitUpdateRequestDto): HabitUidDto

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habitUidDto: HabitUidDto)

    @POST("habit_done")
    suspend fun markDoneHabit(@Body habitDoneDto: HabitDoneResponseDto)

}