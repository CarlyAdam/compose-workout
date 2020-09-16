package com.carlyadam.compose.network

import com.carlyadam.compose.data.api.ApiService
import com.carlyadam.compose.data.model.Post
import com.carlyadam.compose.repo.PostRepository
import com.carlyadam.compose.utils.MockResponseFileReader
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class ApiUnitTest {

    private var mockWebServer = MockWebServer()
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun `testing retrofit service`() = runBlocking {

        //given
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("postResponse.json").content)
        mockWebServer.enqueue(response)

        //val postList = Gson().toJson(response.getBody()!!.readUtf8(), Array<Post>::class.java)
        var postList = Gson().fromJson(response.getBody()!!.readUtf8(),Array<Post>::class.java)


        // when
        val listPost = apiService.getPosts()

        //then
        assertEquals(listPost.body()!!.size, postList.size)
        assertEquals(listPost.body()!![0].title, postList[0].title)
    }


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}
