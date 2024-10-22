package com.sunaa.bishaldady.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScreenDadyView(
    meViewModel: MeViewModel = viewModel()
) {
    val showBabyDialog by meViewModel.showBabyDialog
    val showBigBabyDialog by meViewModel.showBigBabyDialog

    when {
        showBigBabyDialog -> ScreenBigBaby(
            onDismiss = { meViewModel.showBigBabyDialog.value = false },
            onConfirm = { enteredName ->
                meViewModel.addBigBaby(enteredName)
                meViewModel.showBigBabyDialog.value = false
            }
        )
        showBabyDialog -> ScreenBaby(
            onDismiss = { meViewModel.showBabyDialog.value = false },
            onConfirm = { childName,motherName ->
                meViewModel.addChild(childName,motherName)
                meViewModel.showBabyDialog.value = false
            }
        )
        else -> ScreenMain(meViewModel)
    }

}

@Composable
fun ScreenMain(meViewModel: MeViewModel) {
    val uiData by meViewModel.uiData.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    meViewModel.showBabyDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "NEW BABY", color = Color.White)
            }
            Spacer(Modifier.width(10.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    meViewModel.showBigBabyDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "NEW BIG BABY", color = Color.White)
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            Text(text = "HIS NAME IS SAM", modifier = Modifier.padding(bottom = 10.dp))
            Text(text = "HIS BROTHER NAME IS NABIN", modifier = Modifier.padding(bottom = 10.dp))
            LazyColumn {
                item {
                    Text(text = "WIFES")
                }
                items(uiData.wifes){wife ->
                    Text(text = "CONGRATS HE MARRIED TO ${wife}")
                }
            }
            LazyColumn {
                item {
                    Text(text = "CHILDS")
                }
                items(uiData.sons){son ->
                    Text(text = "CONGRATS HE HAVE BABY ${son.name} FROM ${son.motherName}")

                }
            }

        }
    }
}