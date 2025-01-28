package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.repository.ProyekRepository
import com.example.uasa1.ui.view.Proyek.DestinasiUpdateProyek
import kotlinx.coroutines.launch

class UpdateProyekViewModel(
    savedStateHandle: SavedStateHandle,
    private val proyekRepository: ProyekRepository
) : ViewModel() {

    var updateProyekUiState by mutableStateOf(InsertProyekUiState())
        private set

    private val _idProyek: Int = checkNotNull(savedStateHandle[DestinasiUpdateProyek.ID_PROYEK])

    init {
        viewModelScope.launch {
            val proyek = proyekRepository.getProyekById(_idProyek).data
            updateProyekUiState = proyek.toUiStateProyek()
        }
    }

    fun updateInsertProyekState(insertProyekEvent: InsertProyekEvent) {
        updateProyekUiState = updateProyekUiState.copy(insertProyekEvent = insertProyekEvent)
    }

    suspend fun updateProyek() {
        try {
            proyekRepository.updateProyek(_idProyek, updateProyekUiState.insertProyekEvent.toProyek())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
