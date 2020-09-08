package com.carlyadam.compose.data.api

import com.carlyadam.compose.data.model.User
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("users.json")
    suspend fun getUsers(): Response<List<User>>


}
