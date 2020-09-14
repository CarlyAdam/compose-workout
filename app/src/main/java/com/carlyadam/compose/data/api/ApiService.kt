package com.carlyadam.compose.data.api

import com.carlyadam.compose.data.model.Post
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("27e1a5e4")
    suspend fun getPosts(): Response<List<Post>>


}
