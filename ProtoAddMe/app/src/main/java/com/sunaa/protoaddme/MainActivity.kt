package com.sunaa.protoaddme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sunaa.protoaddme.mainfeature.presentation.ScreenBaseView
import com.sunaa.protoaddme.ui.theme.ProtoAddMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProtoAddMeTheme {
                ScreenBaseView()
            }
        }
    }
}

