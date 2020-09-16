package com.carlyadam.compose.repo

import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.model.Post
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class PostRepoUnitTest {


    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `testing post repository`() = runBlocking {

        //given
        val postRepository = mockk<PostRepository>()
        val fakeResponse = flowOf<Result<List<Post>>>()

        //when
        coEvery { postRepository.getPosts() } returns fakeResponse
        val result = postRepository.getPosts()

        // then
        assertEquals(fakeResponse, result)
    }


}
