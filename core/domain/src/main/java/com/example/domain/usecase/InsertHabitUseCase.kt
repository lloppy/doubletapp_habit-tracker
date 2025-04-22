package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import com.example.model.Habit
import javax.inject.Inject

class InsertHabitUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun invoke(habit: Habit): EmptyResult<DataError> {
        localDataSource.insertHabit(habit)
            .onError { error ->
                return Result.Error(error)
            }

        remoteDataSource.updateHabit(habit)
            .onError { error ->
                return Result.Error(error)
            }

        return Result.Success(Unit)
    }
}