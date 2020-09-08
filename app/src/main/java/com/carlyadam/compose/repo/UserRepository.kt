package com.carlyadam.compose.repo

import android.content.Context
import android.util.Log
import com.carlyadam.compose.R
import com.carlyadam.compose.data.api.ApiService
import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.api.safeApiCall
import com.carlyadam.compose.data.model.User
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) {
    suspend fun users(
    ) = safeApiCall(
        call = {
            getUsers()
        },
        errorMessage = context.getString(R.string.no_connection)
    )

    private suspend fun getUsers(
    ): Result<List<User>> {
        val response = apiService.getUsers()
        Log.i("REEESSS", response.code().toString())
        if (response.isSuccessful)
            return Result.Success(response.body()!!)
        return Result.Error(IOException(context.getString(R.string.no_connection)))
    }


}