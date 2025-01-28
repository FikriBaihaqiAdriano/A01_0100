package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.repository.TugasRepository
import com.example.uasa1.ui.view.Tugas.DestinasiUpdateTugas
import kotlinx.coroutines.launch

class UpdateTugasViewModel(
    savedStateHandle: SavedStateHandle,
    private val tugasRepository: TugasRepository
) : ViewModel() {

    var tugasUpdateUiState by mutableStateOf(InsertTugasUiState())
        private set

    private val _idTugas: Int = checkNotNull(savedStateHandle[DestinasiUpdateTugas.ID_TUGAS])

    init {
        viewModelScope.launch {
            val tugas = tugasRepository.getTugasById(_idTugas).data
            tugasUpdateUiState = tugas.toUiStateTugas()
        }
    }

    fun updateInsertTugasState(insertTugasEvent: InsertTugasEvent) {
        tugasUpdateUiState = tugasUpdateUiState.copy(insertTugasEvent = insertTugasEvent)
    }

    suspend fun updateTugas() {
        try {
            tugasRepository.updateTugas(
                _idTugas,
                tugasUpdateUiState.insertTugasEvent.toTugas()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

