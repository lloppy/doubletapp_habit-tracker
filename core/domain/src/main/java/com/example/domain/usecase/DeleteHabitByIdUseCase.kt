package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.map
import com.example.domain.util.onError
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteHabitByIdUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun invoke(id: Int): EmptyResult<DataError> {
        val habit = localDataSource.getHabitById(id = id).first()
            ?: return Result.Error(DataError.Local.NOT_FOUND)

        remoteDataSource.deleteHabit(habit.uid)
            .onError { error ->
                return Result.Error(error)
            }
            .map {
                localDataSource.deleteHabit(habit)
            }

        return Result.Success(Unit)
    }
}
