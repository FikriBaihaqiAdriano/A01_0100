package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.repository.TimRepository
import com.example.uasa1.ui.view.Tim.DestinasiUpdateTim
import kotlinx.coroutines.launch

class UpdateTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val timRepository: TimRepository
) : ViewModel() {

    var TupdateUiState by mutableStateOf(InsertTimUiState())
        private set

    private val _idTim: Int = checkNotNull(savedStateHandle[DestinasiUpdateTim.ID_TIM])

    init {
        viewModelScope.launch {
            val tim = timRepository.getTimById(_idTim).data
            TupdateUiState = tim.toUiStateTim()
        }
    }

    fun updateInsertTimState(insertTimEvent: InsertTimEvent) {
        TupdateUiState = TupdateUiState.copy(insertTimEvent = insertTimEvent)
    }

    suspend fun updateTim() {
        try {
            timRepository.updateTim(_idTim, TupdateUiState.insertTimEvent.toTim())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
