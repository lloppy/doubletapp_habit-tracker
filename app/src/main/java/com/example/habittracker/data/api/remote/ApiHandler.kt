package com.example.habittracker.data.api.remote

import com.example.habittracker.model.model.ApiResult
import retrofit2.Response
import java.io.IOException

object ApiHandler {

    suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> = try {

        val response = apiCall()
        when {
            response.isSuccessful && response.body() != null ->
                ApiResult.Success(response.body()!!)

            response.isSuccessful ->
                ApiResult.Error(response.code(), "Empty response")

            else ->
                ApiResult.Error(response.code(), parseError(response.code()))
        }
    } catch (e: IOException) {
        ApiResult.Error(-1, "No internet")
    } catch (e: Exception) {
        ApiResult.Error(-1, "Request failed")
    }

    private fun parseError(code: Int): String = when (code) {
        400 -> "Bad request"
        401 -> "Unauthorized"
        500 -> "Server error"
        else -> "Error $code"
    }
}