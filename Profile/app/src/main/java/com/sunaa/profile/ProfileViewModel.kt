package com.sunaa.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStore<UserDetails>
) : ViewModel() {
    var name: MutableState<String> = mutableStateOf("")
    var age: MutableState<String> = mutableStateOf("")
    var uri: MutableState<Uri?> = mutableStateOf(null)
    var fileSavedPath : MutableState<String> = mutableStateOf("")

    val userFlow: Flow<UserDetails> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(UserDetails.getDefaultInstance())
        } else {
            throw exception
        }
    }

    fun savePreferences() {
        val uriString = uri.value.toString()
        Log.i("urisave",uriString)
        viewModelScope.launch {
            dataStore.updateData { userDetails: UserDetails ->
                userDetails.toBuilder()
                    .setName(name.value)
                    .setAge(age.value)
                    .setImageUrl(fileSavedPath.value)
                    .build()
            }
        }
    }

    fun updateNameState(newValue: String) {
        name.value = newValue
    }

    fun updateAgeState(newValue: String) {
        age.value = newValue
    }

    fun detailProfile() {
        viewModelScope.launch {
            dataStore.updateData { userDetails: UserDetails ->
                userDetails.toBuilder()
                    .clearName()
                    .clearAge()
                    .clearImageUrl()
                    .build()
            }
        }
    }

    fun saveFilePath(filePath : String) {
        fileSavedPath.value = filePath
    }

}