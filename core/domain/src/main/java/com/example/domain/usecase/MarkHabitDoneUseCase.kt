package com.example.domain.usecase

import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import com.example.data.local.entity.Habit
import com.example.data.remote.model.HabitDoneResponse
import javax.inject.Inject

class MarkHabitDoneUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun execute(habit: com.example.data.local.entity.Habit): EmptyResult<DataError> {
        val response = com.example.data.remote.model.HabitDoneResponse(
            date = (System.currentTimeMillis() / 1000).toInt(),
            habitUid = habit.uid
        )

        remoteDataSource.markDoneHabit(response)
            .onError { error ->
                return Result.Error(error)
            }

        return Result.Success(Unit)
    }
}