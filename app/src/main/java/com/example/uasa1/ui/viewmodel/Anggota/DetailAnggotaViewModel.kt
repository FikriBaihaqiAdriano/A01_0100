package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Anggota
import com.example.uasa1.repository.AnggotaTimRepository
import kotlinx.coroutines.launch

class DetailAnggotaTimViewModel(private val anggotaTimRepository: AnggotaTimRepository) : ViewModel() {

    var AnggotaTimDetailUiState by mutableStateOf(AnggotaTimDetailUiState())
        private set

    fun AnggotaTimDetail(id_anggota: Int) {
        viewModelScope.launch {
            AnggotaTimDetailUiState = AnggotaTimDetailUiState(isLoading = true)
            try {
                val anggotaTimDetailResponse = anggotaTimRepository.getAnggotaTimById(id_anggota)
                if (anggotaTimDetailResponse.status) {
                    AnggotaTimDetailUiState = AnggotaTimDetailUiState(anggotaTimDetailEvent = anggotaTimDetailResponse.data.toAnggotaDetailEvent())
                } else {
                    AnggotaTimDetailUiState = AnggotaTimDetailUiState(isError = true, errorMessage = anggotaTimDetailResponse.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                AnggotaTimDetailUiState = AnggotaTimDetailUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class AnggotaTimDetailUiState(
    val anggotaTimDetailEvent: InsertAnggotaTimEvent = InsertAnggotaTimEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isAnggotaTimDetailEventNotEmpty: Boolean
        get() = anggotaTimDetailEvent != InsertAnggotaTimEvent()
}

fun Anggota.toAnggotaDetailEvent(): InsertAnggotaTimEvent {
    return InsertAnggotaTimEvent(
        id_anggota = id_anggota,
        id_tim = id_tim,
        nama_anggota = nama_anggota,
        peran = peran
    )
}
