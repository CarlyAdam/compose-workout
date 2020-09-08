package com.carlyadam.compose.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.Observer
import com.carlyadam.compose.data.states.UserState
import com.carlyadam.compose.ui.composables.UsersListUi
import com.carlyadam.compose.utils.Coroutines
import com.carlyadam.compose.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val usersState: UserState = UserState()

    private val usersViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usersViewModel.responseLiveData.observe(this, Observer {
            usersState.users.addAll(it)
            setContent {
                UsersListUi.addList(usersState)
            }
        })
        usersViewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        getData()
    }

    private fun getData() = Coroutines.main {
        usersViewModel.users()
    }
}


