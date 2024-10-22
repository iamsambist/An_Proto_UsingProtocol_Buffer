package com.sunaa.bishaldady.ui.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sunaa.bishaldady.data.Son
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiData(
    val sons: MutableList<Son> = mutableListOf(Son("BISHAL", "UPSANA")),
    val wifes: MutableList<String> = mutableListOf("UPSANA")
)

class MeViewModel : ViewModel() {

    val showBabyDialog: MutableState<Boolean> = mutableStateOf(false)
    val showBigBabyDialog: MutableState<Boolean> = mutableStateOf(false)

    private val _uiData = MutableStateFlow(UiData())
    val uiData: StateFlow<UiData> = _uiData.asStateFlow()

    fun addBigBaby(enteredName: String) {
        _uiData.update { currentState ->
           currentState.copy(
               wifes = currentState.wifes.toMutableList().apply {
                   add(enteredName.uppercase())
               }
           )
        }
    }

    fun addChild(childName: String, motherName: String) {
        _uiData.update { currentState ->
            if(currentState.wifes.contains(motherName.uppercase())){
                currentState.copy(
                    sons = currentState.sons.toMutableList().apply {
                        add(Son(childName.uppercase(),motherName.uppercase()))
                    }
                )
            }else{
                currentState
            }
        }
    }


}