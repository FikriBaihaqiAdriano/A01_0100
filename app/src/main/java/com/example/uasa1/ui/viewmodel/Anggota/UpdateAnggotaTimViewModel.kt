package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.repository.AnggotaTimRepository
import com.example.uasa1.ui.view.Anggota.DestinasiUpdateAnggotaTim
import kotlinx.coroutines.launch

class UpdateAnggotaTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaTimRepository: AnggotaTimRepository
) : ViewModel() {

    var AnggotaTimUpdateUiState by mutableStateOf(InsertAnggotaTimUiState())
        private set

    private val _idAnggota: Int = checkNotNull(savedStateHandle[DestinasiUpdateAnggotaTim.ID_ANGGOTA])

    init {
        viewModelScope.launch {
            val anggotaTim = anggotaTimRepository.getAnggotaTimById(_idAnggota).data
            AnggotaTimUpdateUiState = anggotaTim.toUiStateAnggotaTim()
        }
    }

    fun updateInsertAnggotaTimState(insertAnggotaTimEvent: InsertAnggotaTimEvent) {
        AnggotaTimUpdateUiState = AnggotaTimUpdateUiState.copy(insertAnggotaTimEvent = insertAnggotaTimEvent)
    }

    suspend fun updateAnggotaTim() {
        try {
            anggotaTimRepository.updateAnggotaTim(_idAnggota, AnggotaTimUpdateUiState.insertAnggotaTimEvent.toAnggotaTim())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
