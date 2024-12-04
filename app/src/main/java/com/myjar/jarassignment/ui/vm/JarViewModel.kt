package com.myjar.jarassignment.ui.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    var uiState by mutableStateOf(JarUiState())
        private set

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())


    fun fetchData() {
        viewModelScope.launch {
            repository.fetchResults().collect {
                uiState = uiState.copy(data = it)
            }
        }
    }
}

data class JarUiState(
    val data: List<ComputerItem> = emptyList()
)