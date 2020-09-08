package com.carlyadam.compose.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlyadam.compose.data.api.Result
import com.carlyadam.compose.data.model.User
import com.carlyadam.compose.repo.UserRepository
import com.carlyadam.compose.utils.Coroutines
import kotlinx.coroutines.Job

class UserViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) :
    ViewModel() {

    private var userJob: Job? = null

    private val _responseLiveData = MutableLiveData<List<User>>()
    val responseLiveData: LiveData<List<User>> get() = _responseLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun users() {
        userJob = Coroutines.io {
            when (val result = userRepository.users()) {
                is Result.Success -> _responseLiveData.postValue(result.data)
                is Result.Error -> _errorLiveData.postValue(result.exception.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        userJob?.cancel()
    }


}