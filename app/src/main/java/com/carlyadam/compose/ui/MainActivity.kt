package com.carlyadam.compose.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.carlyadam.compose.data.model.Post
import com.carlyadam.compose.utils.Coroutines
import com.carlyadam.compose.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val postViewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            addPostList(postViewModel)
        }

        getData()
    }

    private fun getData() = Coroutines.main {
        postViewModel.posts()
    }
}

@Composable
fun addPostList(postViewModel: PostViewModel) {
    val response by postViewModel.responseLiveData.observeAsState()
    val error by postViewModel.errorLiveData.observeAsState()

    response?.let {
        showPosts(it)
    }

    error?.let {
        showError(error!!)
    }
}

@Composable
fun showPosts(postList: List<Post>) {
    MaterialTheme {
        Column {
            postList.forEach {
                MaterialCardComponent(it)
            }
        }
    }
}

@Composable
fun showError(error: String) {
    val context = ContextAmbient.current
    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
}

@Composable
fun MaterialCardComponent(post: Post) {
    val context = ContextAmbient.current
    Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                    .clickable(onClick = {
                        Toast.makeText(
                                context,
                                "You tap on post: ${post.title}",
                                Toast.LENGTH_SHORT
                        ).show()
                    })
                    .padding(8.dp)
    ) {
        ListItem(text = {
            Text(text = post.id.toString())
        }, secondaryText = {
            Text(text = post.title)
        })
    }
}


