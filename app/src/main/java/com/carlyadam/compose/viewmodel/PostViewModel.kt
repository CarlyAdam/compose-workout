package com.carlyadam.compose.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.model.Post
import com.carlyadam.compose.repo.PostRepository
import com.carlyadam.compose.utils.Coroutines
import kotlinx.coroutines.Job

class PostViewModel @ViewModelInject constructor(
        private val postRepository: PostRepository
) :
        ViewModel() {

    private var postJob: Job? = null

    private val _responseLiveData = MutableLiveData<List<Post>>()
    val responseLiveData: LiveData<List<Post>> get() = _responseLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun posts() {
        postJob = Coroutines.io {
            when (val result = postRepository.posts()) {
                is Result.Success -> _responseLiveData.postValue(result.data)
                is Result.Error -> _errorLiveData.postValue(result.exception.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        postJob?.cancel()
    }


}