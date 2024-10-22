package com.sunaa.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sunaa.profile.ui.view.EditProfileScreenView
import com.sunaa.profile.ui.view.ProfileHomeView

@Composable
fun BaseProfileView(
    profileViewModel: ProfileViewModel = viewModel()
) {

    val prefState by profileViewModel.userFlow.collectAsState(initial = UserDetails.getDefaultInstance())
    if (prefState.name == "") {
        EditProfileScreenView(profileViewModel)
    } else {
        val name = prefState.name
        val age = prefState.age
        val filePath = prefState.imageUrl
        ProfileHomeView(profileViewModel,
            name, age, filePath)
    }
}