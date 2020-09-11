package com.carlyadam.compose.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.carlyadam.compose.ui.composables.UsersListUi
import com.carlyadam.compose.utils.Coroutines
import com.carlyadam.compose.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val usersViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UsersListUi.addList(usersViewModel)
        }

        getData()
    }

    private fun getData() = Coroutines.main {
        usersViewModel.users()
    }
}


