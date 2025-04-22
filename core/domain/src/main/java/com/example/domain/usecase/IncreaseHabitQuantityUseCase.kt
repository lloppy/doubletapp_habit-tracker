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
    private val remoteDataSource: RemoteDataSource,
) {
    suspend operator fun invoke(id: Int): EmptyResult<DataError> {
        val habit = localDataSource.getHabitById(id = id).first()
            ?: return Result.Error(DataError.Local.NOT_FOUND)

        if (habit.quantity < habit.repeatedTimes) {
            val increasedHabit = habit.copy(quantity = habit.quantity + 1)

            localDataSource.insertHabit(increasedHabit)
                .onError { error ->
                    return Result.Error(error)
                }

            remoteDataSource.updateHabit(increasedHabit)
                .onError { error ->
                    return Result.Error(error)
                }

            return Result.Success(Unit)

        }
        return Result.Success(Unit)
    }
}