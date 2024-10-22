package com.sunaa.protoaddme.mainfeature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunaa.protoaddme.UserInfo

@Composable
fun ScreenBaseView(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var anVisibility by remember { mutableStateOf(false) }
    val userList by mainViewModel.userListDataFlow.collectAsState(initial = UserInfo.UserList.getDefaultInstance())
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        anVisibility = !anVisibility
                        mainViewModel.resetErrorFlags()

                    },
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (anVisibility) "HIDE" else "ADD",
                        color = Color.Black
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(visible = anVisibility,
                    enter = fadeIn(tween(800)),
                    exit = fadeOut(tween(700)),
                    content = { ScreenDisplayView(mainViewModel,
                        userList.usersCount) })
            }


        }

        VerticalDivider(
            thickness = 2.dp,
            color = Color.Black
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("USERS LIST")

            if (userList.usersCount == 0) {
                Text(text = "NO USER HERE")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, top = 10.dp)
            ) {
                items(items = userList.usersList.toList()) { item: UserInfo.User? ->
                    Text(text = item?.name.toString())
                    Text(text = item?.profession.toString())
                }
            }
        }
    }
}
