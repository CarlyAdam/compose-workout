package com.carlyadam.compose.ui.composables

import android.widget.Toast
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ContextAmbient
import com.carlyadam.compose.data.model.User
import com.carlyadam.compose.viewmodel.UserViewModel


object UsersListUi {

    @Composable
    fun addList(userViewModel: UserViewModel) {
        val response by userViewModel.responseLiveData.observeAsState()
        val error by userViewModel.errorLiveData.observeAsState()

        response?.let {
           showUsers(it)
        }

        error?.let {
            showError(error!!)
        }
    }

    @Composable
    fun showUsers(usersList: List<User>) {
        MaterialTheme {
            Column {
                usersList.forEach {
                    Column {
                        Row {
                            Text(text = it.name)
                        }
                        Text(text = it.lastname)
                    }

                }
            }
        }
    }

    @Composable
    fun showError(error: String) {
        val context = ContextAmbient.current
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
    }


}