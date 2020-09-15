package com.carlyadam.compose.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.model.Post
import com.carlyadam.compose.repo.PostRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository
) :
    ViewModel() {

    private val _responseLiveData = MutableLiveData<List<Post>>()
    val responseLiveData: LiveData<List<Post>> get() = _responseLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun posts() {
        viewModelScope.launch {
            postRepository.posts().collect { result ->
                when (result) {
                    is Result.Success -> _responseLiveData.postValue(result.data)
                    is Result.Error -> _errorLiveData.postValue(result.exception.message)
                }
            }
        }
    }
}
