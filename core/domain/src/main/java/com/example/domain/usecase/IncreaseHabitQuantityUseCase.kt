package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class IncreaseHabitQuantityUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun execute(id: Int): EmptyResult<DataError> {
        val habit = localDataSource.getHabitById(id).first()

        habit?.let {
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

        return Result.Success(Unit)
    }
}