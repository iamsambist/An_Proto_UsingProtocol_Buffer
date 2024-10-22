package com.sunaa.profile.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sunaa.profile.ProfileViewModel
import com.sunaa.profile.R

@Composable
fun EditProfileScreenView(
    profileViewModel: ProfileViewModel
) {
    val name by profileViewModel.name
    val age by profileViewModel.age
    var seletedUri by profileViewModel.uri
    val context = LocalContext.current


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            seletedUri = uri
            uri?.let {
                val bitmap = getBitmapFromUri(context, it)
                bitmap?.let {

                    profileViewModel.saveFilePath(saveImageToInternalStorage(it, context))
                }
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.wrapContentSize(),
            border = BorderStroke(2.dp, Color.Green),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Text(
                text = "SHOW YOUR CUTE PICTURE",
                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
            )
            AsyncImage(
                model = seletedUri ?: R.mipmap.ic_launcher,
                contentDescription = "PICK AN IMAGE",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .border(6.dp, Color.White)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { newValue ->
                    profileViewModel.updateNameState(newValue)
                },
                label = { Text(text = "Name Field") })

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = age,
                onValueChange = { newValue ->
                    profileViewModel.updateAgeState(newValue)
                },
                label = { Text(text = "Age Field") })

        }
        Button(
            onClick = {
                profileViewModel.savePreferences()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(text = "CREATE PROFILE")
        }
    }
}
fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use {
            BitmapFactory.decodeStream(it)
        }
    }
}


fun saveImageToInternalStorage(bitmap: Bitmap, context: Context): String {
    val filename = "image_${System.currentTimeMillis()}.png"
    context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }
    return filename
}

