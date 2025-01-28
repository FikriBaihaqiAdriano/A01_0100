package com.example.uasa1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasa1.model.Tugas
import com.example.uasa1.repository.TugasRepository
import kotlinx.coroutines.launch

class DetailTugasViewModel(private val tugasRepository: TugasRepository) : ViewModel() {

    var tugasDetailUiState by mutableStateOf(TugasDetailUiState())
        private set

    fun TugasDetail(id_tugas: Int) {
        viewModelScope.launch {
            tugasDetailUiState = TugasDetailUiState(isLoading = true)
            try {
                val tugasDetailResponse = tugasRepository.getTugasById(id_tugas)
                if (tugasDetailResponse.status) {
                    tugasDetailUiState = TugasDetailUiState(
                        tugasDetailEvent = tugasDetailResponse.data.toTugasDetailEvent()
                    )
                } else {
                    tugasDetailUiState = TugasDetailUiState(
                        isError = true,
                        errorMessage = tugasDetailResponse.message
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                tugasDetailUiState = TugasDetailUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}

data class TugasDetailUiState(
    val tugasDetailEvent: DetailTugasEvent = DetailTugasEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isTugasDetailEventNotEmpty: Boolean
        get() = tugasDetailEvent != DetailTugasEvent()
}

data class DetailTugasEvent(
    val id_tugas: Int = 0,
    val id_proyek: Int = 0,
    val id_tim: Int = 0,
    val nama_tugas: String = "",
    val deskripsi_tugas: String = "",
    val prioritas: String = "",
    val status_tugas: String = ""
)

fun Tugas.toTugasDetailEvent(): DetailTugasEvent = DetailTugasEvent(
    id_tugas = id_tugas,
    id_proyek = id_proyek,
    id_tim = id_tim,
    nama_tugas = nama_tugas,
    deskripsi_tugas = deskripsi_tugas,
    prioritas = prioritas,
    status_tugas = status_tugas
)
