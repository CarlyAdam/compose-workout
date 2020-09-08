package com.carlyadam.compose.ui.composables

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.carlyadam.compose.data.states.UserState


object UsersListUi {

    @Composable
    fun addList(state: UserState) {
        MaterialTheme {
            Column {
                state.users.forEach {
                    Column {
                        Row {
                            Text(text = it.userName)
                            Text(text = it.userSurName)
                        }
                        Text(text = it.userSurName)

                    }

                }
            }
        }

    }


}