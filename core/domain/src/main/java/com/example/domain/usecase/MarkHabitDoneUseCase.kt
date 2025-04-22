package com.example.domain.usecase

import com.example.domain.model.HabitDoneResponse
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import com.example.model.Habit
import javax.inject.Inject

class MarkHabitDoneUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(habit: Habit): EmptyResult<DataError> {
        val response = HabitDoneResponse(
            date = System.currentTimeMillis(),
            habitUid = habit.uid
        )

        remoteDataSource.markDoneHabit(response)
            .onError { error ->
                return Result.Error(error)
            }

        return Result.Success(Unit)
    }
}