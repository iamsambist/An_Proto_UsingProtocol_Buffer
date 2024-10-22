package com.sunaa.protoaddme.mainfeature.presentation

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ScreenDisplayView(
    mainViewModel: MainViewModel,
    userCount : Int
) {

    val name by mainViewModel.namestate.collectAsState()
    val profession by mainViewModel.professionState
    val errorFlag by mainViewModel.errorMessage
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetY += dragAmount.y
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .offset { IntOffset(0, offsetY.roundToInt()) },
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "USER NAME", modifier = Modifier.padding(bottom = 5.dp))
            TextField(
                value = name,
                onValueChange = { value ->
                    mainViewModel.updateName(value)
                },
                label = { Text(text = "YOUR NAME") },
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "PROFESSION",
                modifier = Modifier.padding(bottom = 5.dp)
            )
            TextField(
                value = profession,
                onValueChange = { value ->
                    mainViewModel.professionFieldInAction(value)
                },
                label = { Text(text = "JOB NAME ") }
            )
            Spacer(Modifier.height(15.dp))
            Button(
                onClick = { mainViewModel.varifyUserInputs() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Gray),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "ADD USER"
                )
            }
            if (userCount !=0) {
                Button(
                    onClick = { mainViewModel.clearList() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "CLEAR LIST")
                }
            }

            if (errorFlag.isNotEmpty()) {
                Text(text = errorFlag, modifier = Modifier.padding(top = 10.dp))
            }

        }
    }

}
