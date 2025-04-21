package com.example.domain.usecase

import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import com.example.model.domain.Habit
import com.example.model.model.HabitDoneResponse

class MarkHabitDoneUseCase(
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun execute(habit: Habit): EmptyResult<DataError> {
        val response = HabitDoneResponse(
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