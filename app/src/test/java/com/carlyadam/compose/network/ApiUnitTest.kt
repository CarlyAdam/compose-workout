package com.carlyadam.compose.network

import com.carlyadam.compose.data.api.ApiService
import com.carlyadam.compose.repo.PostRepository
import com.carlyadam.compose.utils.MockResponseFileReader
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
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

        val postRepository = PostRepository(apiService)

        // when
        val product = postRepository.posts()

        //then
        assertNotNull(product)
    }


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}
