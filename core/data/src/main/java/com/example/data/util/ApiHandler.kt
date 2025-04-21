package com.example.data.util

import retrofit2.Response
import java.io.IOException

object ApiHandler {

    suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): com.example.model.model.ApiResult<T> = try {

        val response = apiCall()
        when {
            response.isSuccessful && response.body() != null ->
                com.example.model.model.ApiResult.Success(response.body()!!)

            response.isSuccessful ->
                com.example.model.model.ApiResult.Error(response.code(), "Empty response")

            else -> com.example.model.model.ApiResult.Error(response.code(), parseError(response.code()))
        }
    } catch (e: IOException) {
        com.example.model.model.ApiResult.Error(-1, "No internet")
    } catch (e: Exception) {
        com.example.model.model.ApiResult.Error(-1, "Request failed")
    }

    private fun parseError(code: Int): String = when (code) {
        400 -> "Bad request"
        401 -> "Unauthorized"
        500 -> "Internal Server error"
        else -> "Error $code"
    }
}