package com.sunaa.profile.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.sunaa.profile.ProfileViewModel
import java.io.File

@Composable
fun ProfileHomeView(
    profileViewModel: ProfileViewModel,
    name: String, age: String, uriString: String
) {
    val context = LocalContext.current
    val imageUri = remember {
        File(context.filesDir, uriString).toUri()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "Profile Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .border(5.dp, Color.Black)
        )

        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = name.uppercase()
        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = "${age} YEARS OLD"
        )
        Button(
            onClick = { profileViewModel.detailProfile() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(text = "DELETE YOUR PROFILE")
        }

    }
}