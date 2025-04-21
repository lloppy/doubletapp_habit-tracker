package com.example.domain.usecase

import com.example.domain.repository.LocalDataSource
import com.example.domain.repository.RemoteDataSource
import com.example.domain.util.DataError
import com.example.domain.util.EmptyResult
import com.example.domain.util.Result
import com.example.domain.util.onError
import kotlinx.coroutines.flow.first

class SyncLocalToRemoteUseCase(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun execute(): EmptyResult<DataError> {
        localDataSource
            .getAllHabits()
            .first()
            .forEach { habit ->
                remoteDataSource.updateHabit(habit)
                    .onError { return Result.Error(it) }
            }

        return Result.Success(Unit)
    }
}