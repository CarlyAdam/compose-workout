package com.carlyadam.compose.repo

import android.util.Log
import com.carlyadam.compose.data.api.ApiService
import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.api.safeApiCall
import com.carlyadam.compose.data.model.Post
import java.io.IOException
import javax.inject.Inject

class PostRepository @Inject constructor(
        private val apiService: ApiService
) {
    suspend fun posts(
    ) = safeApiCall(
            call = {
                getPosts()
            },
            errorMessage = "No connection"
    )

    private suspend fun getPosts(
    ): Result<List<Post>> {
        val response = apiService.getPosts()
        if (response.isSuccessful)
            return Result.Success(response.body()!!)
        return Result.Error(IOException("No connection"))
    }


}