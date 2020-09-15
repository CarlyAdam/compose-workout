package com.carlyadam.compose.repo

import com.carlyadam.compose.data.api.ApiService
import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.api.safeApiCall
import com.carlyadam.compose.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun posts() = safeApiCall(
        call = {
            getPosts()
        },
        errorMessage = "No connection"
    )

    private suspend fun getPosts(): Flow<Result<List<Post>>> = flow {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            emit(Result.Success(response.body()!!))
        } else {
            emit(Result.Error(IOException("No connection")))
        }
    }.flowOn(Dispatchers.IO)
}
