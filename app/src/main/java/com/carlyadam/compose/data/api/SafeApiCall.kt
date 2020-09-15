package com.carlyadam.compose.data.api

import android.accounts.NetworkErrorException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Flow<Result<T>>,
    errorMessage: String
): Flow<Result<T>> =
    try {
        call.invoke()
    } catch (e: Exception) {
        flowOf(Result.Error(IOException(errorMessage, e)))
        /*when (e) {
            is SocketTimeoutException -> {
                flowOf(Result.Error(IOException(e.message)))
            }
            is IOException -> {
                Log.i("AAAA",e.message.toString())
                flowOf(Result.Error(IOException(e.message.toString())))
            }
            is HttpException -> {
                val body = e.response()?.errorBody()
                flowOf(Result.Error(IOException(body.toString())))
            }
            is NetworkErrorException -> {
                flowOf(Result.Error(IOException(e.message)))
            }
            else -> {
                flowOf(Result.Error(IOException(e.message)))
            }
        }*/
    }