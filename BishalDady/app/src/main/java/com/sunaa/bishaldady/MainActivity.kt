package com.sunaa.bishaldady

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sunaa.bishaldady.ui.theme.BishalDadyTheme
import com.sunaa.bishaldady.ui.view.ScreenDadyView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BishalDadyTheme {
                ScreenDadyView()
            }
        }
    }
}