package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DecreaseHabitQuantityUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {
    suspend operator fun invoke(id: Int): EmptyResult<DataError> {
        val habit = localDataSource.getHabitById(id = id).first()
            ?: return Result.Error(DataError.Local.NOT_FOUND)

        if (habit.quantity > 1) {
            val decreasedHabit = habit.copy(quantity = habit.quantity - 1)

            localDataSource.insertHabit(decreasedHabit)
                .onError { error ->
                    return Result.Error(error)
                }

            remoteDataSource.updateHabit(decreasedHabit)
                .onError { error ->
                    return Result.Error(error)
                }

            return Result.Success(Unit)
        }

        return Result.Success(Unit)
    }
}