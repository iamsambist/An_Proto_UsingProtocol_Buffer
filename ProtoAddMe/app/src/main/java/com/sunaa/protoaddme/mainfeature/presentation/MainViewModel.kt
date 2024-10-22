package com.sunaa.protoaddme.mainfeature.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunaa.protoaddme.UserInfo.User
import com.sunaa.protoaddme.UserInfo.UserList
import com.sunaa.protoaddme.mainfeature.domain.InputVarifiaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val inputVarifiaction: InputVarifiaction,
    private val dataStore: DataStore<UserList>
) :
    ViewModel() {

    val userListDataFlow: Flow<UserList> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(UserList.getDefaultInstance())
        } else {
            throw exception
        }
    }


    // Mutable list that holds dynamic data
    private val _items = mutableStateListOf<User>()
    val items: List<User> get() = _items

    private val _nameState = MutableStateFlow("")
    val namestate: StateFlow<String> = _nameState.asStateFlow()
    val professionState: MutableState<String> = mutableStateOf("")
    val errorMessage: MutableState<String> = mutableStateOf("")

    fun updateName(value: String) {
        _nameState.value = value
    }

    fun professionFieldInAction(value: String) {
        professionState.value = value
    }

    fun varifyUserInputs() {
        if (inputVarifiaction.isValid(_nameState.value)) {
            if (inputVarifiaction.isValid(professionState.value)) {
                saveUserInformation(_nameState.value, professionState.value)
            } else {
                errorMessage.value = "BACKWASS PROFESSION"
            }
        } else {
            errorMessage.value = "BACKWASS NAME"

        }
    }

    private fun saveUserInformation(value: String, value1: String) {
        val newUser = User.newBuilder()
            .setName(value)
            .setProfession(value1)
            .build()
        viewModelScope.launch {
            dataStore.updateData { userList: UserList ->
                userList.toBuilder().addUsers(newUser).build()
            }
        }
    }


    fun resetErrorFlags() {
        if (errorMessage.value.isNotEmpty()) {
            errorMessage.value = ""
        }
    }

    fun clearList() {
       viewModelScope.launch {
           dataStore.updateData {
               userList: UserList ->
               userList.toBuilder().clearUsers().build()
           }
       }
    }
}