package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import kotlinx.coroutines.flow.first

class IncreaseHabitQuantityUseCase(
    private val localDataSource: LocalDataSource,
    private val updateUseCase: InsertHabitUseCase,
    private val markDoneUseCase: MarkHabitDoneUseCase
) {
    suspend fun execute(id: Int): EmptyResult<DataError> {
        val habit = localDataSource.getHabitById(id).first()

        habit?.let {
            updateUseCase.execute(
                habit = it.copy(quantity = it.quantity + 1)
            )

            if (it.quantity == it.repeatedTimes) {
                markDoneUseCase.execute(it)
            }
        }

        return Result.Success(Unit)
    }
}