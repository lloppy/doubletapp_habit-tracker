package com.example.domain.usecase

import com.example.domain.model.HabitDoneResponseModel
import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import com.example.model.Habit
import javax.inject.Inject

class MarkHabitDoneUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(habit: Habit): EmptyResult<DataError> {
        val response = HabitDoneResponseModel(
            date = System.currentTimeMillis(),
            habitUid = habit.uid
        )

        // если пользователь пометил привычку как Done - значит
        // он завершил выполнение привычки: выполнил все разы quantity из намеченных раз repeatedTimes

        val updatedHabit = habit.copy(quantity = habit.repeatedTimes)

        localDataSource.insertHabit(updatedHabit)
            .onError { error ->
                return Result.Error(error)
            }

        remoteDataSource.updateHabit(updatedHabit)
            .onError { error ->
                return Result.Error(error)
            }

        remoteDataSource.markDoneHabit(response)
            .onError { error ->
                return Result.Error(error)
            }

        return Result.Success(Unit)
    }
}